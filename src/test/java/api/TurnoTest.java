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
    String fechaIngresoPaciente = localDateAdapter.localDateToString(LocalDate.now(), "yyyy-MM-dd");

    private OdontologoController odontologoController = new OdontologoController();
    private PacienteController pacienteController = new PacienteController();

    Domicilio domicilioPaciente = new Domicilio("Calle Test", 1212, "Localidad Test");
    Paciente pacienteUno = new Paciente("PacienteTurnoUno", "TestTurnoPacUno", "12345678", fechaIngresoPaciente, domicilioPaciente);
    Odontologo odontologoUno = new Odontologo("OdontologoTurnoUno", "TestTurnoOdoUno", "ODOTURNO001");
    Turno turnoNew1 = null;


    @BeforeAll
    public void before() {
        pacienteUno = pacienteController.pacientePost(pacienteUno, base64CredentialsAdmin);
        odontologoUno = odontologoController.odontologoPost(odontologoUno, base64CredentialsAdmin);

        turnoNew1 = new Turno(pacienteUno, odontologoUno, fechaHoraString);
    }


    @Test
    @Order(1)
    public void getTurnosIsEmpty() throws MissingPropertyException, IOException {
        TurnoController turnoController = new TurnoController();

        Response res = turnoController.getTurnos(base64CredentialsAdmin);

        List<Map<String, Object>> listTurnos = res.jsonPath().getList(".");

        System.out.println("LISTA TURNOS EMPTY:" + listTurnos);


        Assert.assertTrue( listTurnos.isEmpty(), "La lista de turnos no está vacía");
    }

    @Test
    @Order(2)
    public void postTurnoWithId1() throws MissingPropertyException, IOException {
        TurnoController turnoController = new TurnoController();

        Response res = turnoController.postTurno(turnoNew1, base64CredentialsAdmin);

        Paciente paciente = turnoNew1.getPaciente();
        Odontologo odontologo = turnoNew1.getOdontologo();

        System.out.println("TURNONEW1: " + turnoNew1);

        JsonPath jsonPathRes = new JsonPath(res.getBody().asString());

        System.out.println("JSONRESPOST: " + jsonPathRes);

        Long id = jsonPathRes.getLong("id");

        String nombreCompletoPaciente = jsonPathRes.getString("paciente");

        String nombreCompletoOdontologo = jsonPathRes.getString("odontologo");

        String fechaYHora = jsonPathRes.getString("fechaYHora");

        LocalDateTime fechaYHoraLDT = localDateTimeAdapter.stringToLocalDateTimeResTurno(fechaYHora,"dd-MM-yyyy HH:mm");

        String fechaYHoraFormateada = localDateTimeAdapter.localDateTimeToString(fechaYHoraLDT, "yyyy-MM-dd HH:mm");

        Turno turno = new Turno(paciente, odontologo, fechaYHoraFormateada);

        turno.setId(id);

        System.out.println("TURNO LUEGO DE CREADO: " + turno);

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

        System.out.println("LISTA SIZE 1: " + listTurnos);

        Assert.assertEquals(listTurnos.size(), 1);
    }

//    @Test
//    @Order(4)
//    public void postTurnoWithId2() throws MissingPropertyException, IOException {
//        TurnoController turnoController = new TurnoController();
//
//        Response res = turnoController.postTurno(turnoNew2, base64CredentialsAdmin);
//
//        Turno turno = res.as(Turno.class);
//
//        Assert.assertEquals(turno.getId(), 1);
//    }

}
