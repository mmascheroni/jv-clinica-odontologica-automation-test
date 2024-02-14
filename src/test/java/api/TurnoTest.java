package api;

import config.ConfigProperties;
import config.UserCredentialsConfig;
import controllers.OdontologoController;
import controllers.PacienteController;
import controllers.TurnoController;
import exceptions.MissingPropertyException;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Domicilio;
import models.Odontologo;
import models.Paciente;
import models.Turno;
import org.junit.jupiter.api.*;
import org.testng.Assert;
import utils.LocalDateAdapter;
import utils.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TurnoTest {

    private LocalDateTimeAdapter localDateTimeAdapter = new LocalDateTimeAdapter();
    private LocalDateAdapter localDateAdapter = new LocalDateAdapter();
    private ConfigProperties configProperties = new ConfigProperties();
    private UserCredentialsConfig userCredentialsConfig = new UserCredentialsConfig(configProperties);

    private String base64CredentialsAdmin = userCredentialsConfig.getUserBase64Credentials("USER_ADMIN", "PASSWORD_ADMIN", "config");
    private String base64CredentialsUser = userCredentialsConfig.getUserBase64Credentials("USER", "PASSWORD_USER", "config");

    public TurnoTest() throws MissingPropertyException, IOException {
    }

    String fechaHoraString = localDateTimeAdapter.localDateTimeToString(LocalDateTime.now().plusDays(5), "yyyy-MM-dd HH:mm");

    String fechaHoraNowString = localDateTimeAdapter.localDateTimeToString(LocalDateTime.now(), "yyyy-MM-dd HH:mm");
    String fechaIngresoPaciente = localDateAdapter.localDateToString(LocalDate.now(), "yyyy-MM-dd");

    private OdontologoController odontologoController = new OdontologoController();
    private PacienteController pacienteController = new PacienteController();

    Domicilio domicilioPaciente = new Domicilio("Calle Test", 1212, "Localidad Test");
    Paciente pacienteUno = new Paciente("PacienteTurnoUno", "TestTurnoPacUno", "12345678", fechaIngresoPaciente, domicilioPaciente);
    Odontologo odontologoUno = new Odontologo("OdontologoTurnoUno", "TestTurnoOdoUno", "ODOTURNO001");

    Paciente pacienteDos = new Paciente("PacienteTurnoDos", "TestTurnoPacDos", "12345678", fechaIngresoPaciente, domicilioPaciente);
    Paciente pacienteNull = null;
    Odontologo odontologoDos = new Odontologo("OdontologoTurnoDos", "TestTurnoOdoDos", "ODOTURNO002");
    Odontologo odontologoNull = null;
    Turno turnoNew1 = null;
    Turno turnoNew2 = null;
    Turno turnoPacienteAndOdontologoNull = null;
    Turno turnoPacienteAndOdontologoNotExists = null;
    Turno turnoPacienteNotExist = null;
    Turno turnoOdontologoNotExist = null;
    Turno turnoFechaLessThanToday = null;

    @BeforeAll
    public void before() {
        pacienteUno = pacienteController.pacientePost(pacienteUno, base64CredentialsAdmin);
        odontologoUno = odontologoController.odontologoPost(odontologoUno, base64CredentialsAdmin);

        pacienteDos = pacienteController.pacientePost(pacienteDos, base64CredentialsAdmin);
        odontologoDos = odontologoController.odontologoPost(odontologoDos, base64CredentialsAdmin);

        turnoNew1 = new Turno(pacienteUno, odontologoUno, fechaHoraString);
        turnoNew2 = new Turno(pacienteUno, odontologoUno, fechaHoraString);
        turnoPacienteAndOdontologoNull = new Turno(pacienteNull, odontologoNull, fechaHoraString);

        turnoPacienteAndOdontologoNotExists = new Turno(new Paciente(100L,"PacienteTurnoDos", "TestTurnoPacDos", "12345678", fechaIngresoPaciente, domicilioPaciente), new Odontologo(100L, "OdontologoTurnoDos", "TestTurnoOdoDos", "ODOTURNO002"), fechaHoraString);

        turnoOdontologoNotExist = new Turno(pacienteUno, new Odontologo(100L, "OdontologoTurnoDos", "TestTurnoOdoDos", "ODOTURNO002"), fechaHoraString);

        turnoPacienteNotExist = new Turno(new Paciente(100L,"PacienteTurnoDos", "TestTurnoPacDos", "12345678", fechaIngresoPaciente, domicilioPaciente), odontologoUno, fechaHoraString);

        turnoFechaLessThanToday = new Turno(pacienteUno, odontologoUno, fechaHoraNowString);
    }


    @Test
    @Order(1)
    public void getTurnosIsEmpty() throws MissingPropertyException, IOException {
        TurnoController turnoController = new TurnoController();
        Response res = turnoController.getTurnos(base64CredentialsAdmin);

        List<Map<String, Object>> listTurnos = res.jsonPath().getList(".");

        Assert.assertTrue( listTurnos.isEmpty(), "La lista de turnos no está vacía");
    }

    @Test
    @Order(2)
    public void postTurnoWithId1() throws MissingPropertyException, IOException {
        TurnoController turnoController = new TurnoController();
        Response res = turnoController.postTurno(turnoNew1, base64CredentialsAdmin);

        Paciente paciente = turnoNew1.getPaciente();
        Odontologo odontologo = turnoNew1.getOdontologo();

        JsonPath jsonPathRes = new JsonPath(res.getBody().asString());
        Long id = jsonPathRes.getLong("id");
        String nombreCompletoPaciente = jsonPathRes.getString("paciente");
        String nombreCompletoOdontologo = jsonPathRes.getString("odontologo");
        String fechaYHora = jsonPathRes.getString("fechaYHora");
        LocalDateTime fechaYHoraLDT = localDateTimeAdapter.stringToLocalDateTimeResTurno(fechaYHora,"dd-MM-yyyy HH:mm");
        String fechaYHoraFormateada = localDateTimeAdapter.localDateTimeToString(fechaYHoraLDT, "yyyy-MM-dd HH:mm");

        Turno turno = new Turno(paciente, odontologo, fechaYHoraFormateada);
        turno.setId(id);

        Assert.assertEquals(id, turno.getId());
        Assert.assertEquals(nombreCompletoPaciente, paciente.getNombre() + " " + paciente.getApellido());
        Assert.assertEquals(nombreCompletoOdontologo, odontologo.getNombre() + " " + odontologo.getApellido());
        Assert.assertEquals(turno.getFechaYHora(), turnoNew1.getFechaYHora() );
    }


    @Test
    @Order(3)
    public void getTurnosSizeIsEquals1() throws MissingPropertyException, IOException {
        TurnoController turnoController = new TurnoController();
        Response res = turnoController.getTurnos(base64CredentialsAdmin);

        List<Map<String, Object>> listTurnos = res.jsonPath().getList(".");

        Assert.assertEquals(listTurnos.size(), 1);
    }

    @Test
    @Order(3)
    public void getTurnoWithId1() throws MissingPropertyException, IOException {
        TurnoController turnoController = new TurnoController();
        Response res = turnoController.getTurno(1L, base64CredentialsAdmin);

        JsonPath jsonPathRes = new JsonPath(res.getBody().asString());
        Long id = jsonPathRes.getLong("id");
        String nombreCompletoPaciente = jsonPathRes.getString("paciente");
        String nombreCompletoOdontologo = jsonPathRes.getString("odontologo");
        String fechaYHora = jsonPathRes.getString("fechaYHora");
        LocalDateTime fechaYHoraLDT = localDateTimeAdapter.stringToLocalDateTimeResTurno(fechaYHora,"dd-MM-yyyy HH:mm");
        String fechaYHoraFormateada = localDateTimeAdapter.localDateTimeToString(fechaYHoraLDT, "yyyy-MM-dd HH:mm");

        Assert.assertEquals(id, 1L);
        Assert.assertEquals(nombreCompletoPaciente, turnoNew1.getPaciente().getNombre() + " " + turnoNew1.getPaciente().getApellido());
        Assert.assertEquals(nombreCompletoOdontologo, turnoNew1.getOdontologo().getNombre() + " " + turnoNew1.getOdontologo().getApellido());
        Assert.assertEquals(fechaYHoraFormateada, turnoNew1.getFechaYHora() );
    }

    @Test
    @Order(4)
    public void postTurnoWithId2() throws MissingPropertyException, IOException {
        TurnoController turnoController = new TurnoController();
        Response res = turnoController.postTurno(turnoNew2, base64CredentialsAdmin);

        Paciente paciente = turnoNew2.getPaciente();
        Odontologo odontologo = turnoNew2.getOdontologo();

        JsonPath jsonPathRes = new JsonPath(res.getBody().asString());
        Long id = jsonPathRes.getLong("id");
        String nombreCompletoPaciente = jsonPathRes.getString("paciente");
        String nombreCompletoOdontologo = jsonPathRes.getString("odontologo");
        String fechaYHora = jsonPathRes.getString("fechaYHora");
        LocalDateTime fechaYHoraLDT = localDateTimeAdapter.stringToLocalDateTimeResTurno(fechaYHora,"dd-MM-yyyy HH:mm");
        String fechaYHoraFormateada = localDateTimeAdapter.localDateTimeToString(fechaYHoraLDT, "yyyy-MM-dd HH:mm");

        Turno turno = new Turno(paciente, odontologo, fechaYHoraFormateada);
        turno.setId(id);

        Assert.assertEquals(id, turno.getId());
        Assert.assertEquals(nombreCompletoPaciente, paciente.getNombre() + " " + paciente.getApellido());
        Assert.assertEquals(nombreCompletoOdontologo, odontologo.getNombre() + " " + odontologo.getApellido());
        Assert.assertEquals(turno.getFechaYHora(), turnoNew2.getFechaYHora() );
    }

    @Test
    @Order(5)
    public void getTurnosSizeIsEquals2() throws MissingPropertyException, IOException {
        TurnoController turnoController = new TurnoController();
        Response res = turnoController.getTurnos(base64CredentialsAdmin);

        List<Map<String, Object>> listTurnos = res.jsonPath().getList(".");

        Assert.assertEquals(listTurnos.size(), 2);
    }

    @Test
    @Order(6)
    public void deleteTurnoWithId1() throws MissingPropertyException, IOException {
        TurnoController turnoController = new TurnoController();
        Response res = turnoController.deleteTurno(1L, base64CredentialsAdmin);

        Assert.assertEquals(res.prettyPrint(), "El turno ha sido eliminado correctamente");
    }

    @Test
    @Order(7)
    public void getTurnosSizeIsEquals1AfterDelete() throws MissingPropertyException, IOException {
        TurnoController turnoController = new TurnoController();
        Response res = turnoController.getTurnos(base64CredentialsAdmin);

        List<Map<String, Object>> listTurnos = res.jsonPath().getList(".");

        Assert.assertEquals(listTurnos.size(), 1);
    }


    @Test
    @Order(8)
    public void postTurnoWithOdontologoAndPacienteNull() throws MissingPropertyException, IOException {
        TurnoController turnoController = new TurnoController();
        Response res = turnoController.postTurno(turnoPacienteAndOdontologoNull, base64CredentialsAdmin);

        JsonPath jsonPathRes = new JsonPath(res.getBody().asString());
        String messagePacienteNull = jsonPathRes.getString("paciente");
        String messageOdontologoNull = jsonPathRes.getString("odontologo");

        Assert.assertEquals(res.getStatusCode(), 400);
        Assert.assertEquals(messagePacienteNull, "El paciente no puede ser nulo");
        Assert.assertEquals(messageOdontologoNull, "El odontologo no puede ser nulo");
    }

    @Test
    @Order(9)
    public void postTurnoWithOdontologoAndPacienteNoExists() throws MissingPropertyException, IOException {
        TurnoController turnoController = new TurnoController();
        Response res = turnoController.postTurno(turnoPacienteAndOdontologoNotExists, base64CredentialsAdmin);

        JsonPath jsonPathRes = new JsonPath(res.getBody().asString());
        String message = jsonPathRes.getString("message");

        Assert.assertEquals(res.getStatusCode(), 400);
        Assert.assertEquals(message, "Recurso no encontrado: El paciente y el odontologo no se encuentran en la base de datos");
    }


    @Test
    @Order(10)
    public void postTurnoWithOdontologoNoExist() throws MissingPropertyException, IOException {
        TurnoController turnoController = new TurnoController();
        Response res = turnoController.postTurno(turnoOdontologoNotExist, base64CredentialsAdmin);

        JsonPath jsonPathRes = new JsonPath(res.getBody().asString());
        String message = jsonPathRes.getString("message");

        Assert.assertEquals(res.getStatusCode(), 400);
        Assert.assertEquals(message, "Recurso no encontrado: El odontologo no se encuentra en la base de datos");
    }

    @Test
    @Order(11)
    public void postTurnoWithPacienteNoExist() throws MissingPropertyException, IOException {
        TurnoController turnoController = new TurnoController();
        Response res = turnoController.postTurno(turnoPacienteNotExist, base64CredentialsAdmin);

        JsonPath jsonPathRes = new JsonPath(res.getBody().asString());
        String message = jsonPathRes.getString("message");

        Assert.assertEquals(res.getStatusCode(), 400);
        Assert.assertEquals(message, "Recurso no encontrado: El paciente no se encuentra en la base de datos");
    }

    @Test
    @Order(12)
    public void postTurnoWithFechaLessThanToday() throws MissingPropertyException, IOException {
        TurnoController turnoController = new TurnoController();
        Response res = turnoController.postTurno(turnoFechaLessThanToday, base64CredentialsAdmin);

        JsonPath jsonPathRes = new JsonPath(res.getBody().asString());
        String message = jsonPathRes.getString("fechaYHora");

        Assert.assertEquals(res.getStatusCode(), 400);
        Assert.assertEquals(message, "La fecha y hora no puede ser anterior al día de hoy, ni a la hora actual");
    }

}
