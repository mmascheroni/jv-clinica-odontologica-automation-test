package api;

import config.ConfigProperties;
import config.UserCredentialsConfig;
import controllers.OdontologoController;
import controllers.PacienteController;
import controllers.TurnoController;
import exceptions.MissingPropertyException;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Odontologo;
import models.Paciente;
import models.Turno;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class TurnoTest {

    private ConfigProperties configProperties = new ConfigProperties();

    private UserCredentialsConfig userCredentialsConfig = new UserCredentialsConfig(configProperties);

    private String base64CredentialsAdmin = userCredentialsConfig.getUserBase64Credentials("USER_ADMIN", "PASSWORD_ADMIN", "config");

    private String base64CredentialsUser = userCredentialsConfig.getUserBase64Credentials("USER", "PASSWORD_USER", "config");

    public TurnoTest() throws MissingPropertyException, IOException {
    }

    LocalDateTime localDateTime = LocalDateTime.now().plusDays(5);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    DateTimeFormatter formatterRes = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    String fechaHoraString = localDateTime.format(formatter);

    private OdontologoController odontologoController = new OdontologoController();

    private PacienteController pacienteController = new PacienteController();

    Paciente pacienteWitId3 = pacienteController.paciente(1L, base64CredentialsAdmin);

//    Paciente pacienteWithId4 = pacienteController.paciente(4L, base64CredentialsAdmin);
//
//    Paciente pacienteWithId5 = pacienteController.paciente(5L, base64CredentialsAdmin);

    Odontologo odontologoWitId3 = odontologoController.odontologo(1L, base64CredentialsAdmin);

//    Odontologo odontologoWithId4 = odontologoController.odontologo(4L, base64CredentialsAdmin);
//
//    Odontologo odontologoWithId5 = odontologoController.odontologo(5L, base64CredentialsAdmin);

    Turno turnoNew1 = new Turno(pacienteWitId3, odontologoWitId3, fechaHoraString );

//    Turno turnoNew2 = new Turno(pacienteWithId4, odontologoWithId4, fechaHoraString );

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

        String resBody = res.getBody().asString();

        JsonPath jsonPathRes = new JsonPath(resBody);

        Long id = jsonPathRes.getLong("id");

        String nombreCompletoPaciente = jsonPathRes.getString("paciente");

        String nombreCompletoOdontologo = jsonPathRes.getString("odontologo");

        String fechaYHora = jsonPathRes.getString("fechaYHora");

        DateTimeFormatter formatoRes = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        LocalDateTime fechaYHoraLDT = LocalDateTime.parse(fechaYHora, formatoRes);

        String fechaYHoraFormateada = fechaYHoraLDT.format(formatter);

        Turno turno = new Turno(paciente, odontologo, fechaYHoraFormateada);

        turno.setId(id);

        Assert.assertEquals(id, turno.getId());
        Assert.assertEquals(turnoNew1.getPaciente().getNombre() + " " + turnoNew1.getPaciente().getApellido(), nombreCompletoPaciente);
        Assert.assertEquals(turnoNew1.getOdontologo().getNombre() + " " + turnoNew1.getOdontologo().getApellido(), nombreCompletoOdontologo);
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
