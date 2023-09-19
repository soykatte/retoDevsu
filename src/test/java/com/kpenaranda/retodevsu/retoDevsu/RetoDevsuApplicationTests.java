package com.kpenaranda.retodevsu.retoDevsu;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.kpenaranda.retodevsu.retoDevsu.service.ClienteService;
import com.kpenaranda.retodevsu.retoDevsu.service.MovimientoService;
import com.kpenaranda.retodevsu.retoDevsu.service.ReporteService;
import com.kpenaranda.retodevsu.retoDevsu.service.impl.ReporteServiceImpl;
import com.kpenaranda.retodevsu.retoDevsu.dto.ClienteDTO;
import com.kpenaranda.retodevsu.retoDevsu.utility.enumerators.Genero;
import com.kpenaranda.retodevsu.retoDevsu.utility.mappers.CuentaMapper;
import com.kpenaranda.retodevsu.retoDevsu.utility.mappers.MovimientoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class RetoDevsuApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ClienteService clienteService;

  @Autowired
  private ReporteService reporteService;

  @Autowired
  private MovimientoService movimientoService;

  @Autowired
  private MovimientoMapper movimientoMapper;

  @Autowired
  private CuentaMapper cuentaMapper;

  @Test
  void contextLoads() {
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    reporteService = new ReporteServiceImpl(clienteService, movimientoService, movimientoMapper);
  }

  @Test
  public void createNewClient() throws Exception {
    mockMvc.perform(
        post("/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Katterin Penaranda\",\r\n    \"genero\": \"Femenino\",\r\n    \"edad\": 30,\r\n    \"identificacion\": \"123456\",\r\n    \"direccion\": \"calle 85\",\r\n    \"telefono\": \"3001234567\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": true\r\n}")
    ).andExpect(status().isCreated());
  }

  @Test
  public void createClientWrongName() throws Exception {
    mockMvc.perform(
        post("/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"K@tterin Penaranda\",\r\n    \"genero\": \"Femenino\",\r\n    \"edad\": 31,\r\n    \"identificacion\": \"124578\",\r\n    \"direccion\": \"cra 24\",\r\n    \"telefono\": \"3012345678\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": true\r\n}")
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void createClientWrongGenre() throws Exception {
    mockMvc.perform(
        post("/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Katterin Penaranda\",\r\n    \"genero\": \"Pansexual\",\r\n    \"edad\": 32,\r\n    \"identificacion\": \"124578\",\r\n    \"direccion\": \"calle 6\",\r\n    \"telefono\": \"3012345678\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": true\r\n}")
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void createClientWrongAge() throws Exception {
    mockMvc.perform(
        post("/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Katterin Penaranda\",\r\n    \"genero\": \"Femenino\",\r\n    \"edad\": 126,\r\n    \"identificacion\": \"124578\",\r\n    \"direccion\": \"calle 8\",\r\n    \"telefono\": \"3012345678\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": true\r\n}")
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void createClientWrongIdentification() throws Exception {
    mockMvc.perform(
        post("/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Katte Penaranda\",\r\n    \"genero\": \"Femenino\",\r\n    \"edad\": 39,\r\n    \"identificacion\": \"114332AAA\",\r\n    \"direccion\": \"Cra 9\",\r\n    \"telefono\": \"3012345678\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": true\r\n}")
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void createClientWrongStatus() throws Exception {
    mockMvc.perform(
        post("/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Katte Penaranda\",\r\n    \"genero\": \"Femenino\",\r\n    \"edad\": 36,\r\n    \"identificacion\": \"124578\",\r\n    \"direccion\": \"Cra 44\",\r\n    \"telefono\": \"3012345678\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": tru\r\n}")
    ).andExpect(status().isBadRequest());
  }


  @Test
  public void createExistingClient() throws Exception {
    // Crear un cliente existente en la base de datos
    ClienteDTO existingCliente = new ClienteDTO();
    existingCliente.setNombre("Katte Penaranda");
    existingCliente.setGenero(Genero.MASCULINO);
    existingCliente.setEdad(37);
    existingCliente.setIdentificacion("124578");
    existingCliente.setDireccion("Cra 55 #12sur-14");
    existingCliente.setTelefono("3012345678");
    existingCliente.setPassword("123456!");
    existingCliente.setEstado(true);
    clienteService.createCliente(existingCliente);

    // Crear un cliente con los mismos datos existentes
    ClienteDTO newCliente = new ClienteDTO();
    newCliente.setNombre("Katte Penaranda");
    newCliente.setGenero(Genero.MASCULINO);
    newCliente.setEdad(37);
    newCliente.setIdentificacion("124578");
    newCliente.setDireccion("Cra 55 #12sur-14");
    newCliente.setTelefono("3012345678");
    newCliente.setPassword("123456!");
    newCliente.setEstado(true);

    // Realizar la solicitud POST para crear el cliente
    ResultActions result = mockMvc.perform(
        post("/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Katte Penaranda\",\r\n    \"genero\": \"Femenino\",\r\n    \"edad\": 37,\r\n    \"identificacion\": \"124578\",\r\n    \"direccion\": \"Cra 55 #12sur-14\",\r\n    \"telefono\": \"3012345678\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": true\r\n}")
    ).andExpect(status().isConflict());
  }

  @Test
  public void getClients() throws Exception {
    mockMvc.perform(
        get("/clientes")
    ).andExpect(status().isOk());
  }

  @Test
  public void getClientById() throws Exception {
    mockMvc.perform(
        get("/clientes/1")
    ).andExpect(status().isOk());
  }

  @Test
  public void getInexistentClientById() throws Exception {
    mockMvc.perform(
        get("/clientes/100")
    ).andExpect(status().isNotFound());
  }

  @Test
  public void updateClient() throws Exception {
    mockMvc.perform(
        put("/clientes/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Katte Penaranda Morelo\",\r\n    \"genero\": \"Femenino\",\r\n    \"edad\": 38,\r\n    \"identificacion\": \"124578\",\r\n    \"direccion\": \"Cra 55 #12sur-14\",\r\n    \"telefono\": \"3012345678\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": true\r\n}")
    ).andExpect(status().isOk());
  }

  @Test
  public void updateUnexistingClient() throws Exception {
    mockMvc.perform(
        put("/clientes/100")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"nombre\": \"Katte Penaranda Morelo\",\r\n    \"genero\": \"Femenino\",\r\n    \"edad\": 38,\r\n    \"identificacion\": \"124578\",\r\n    \"direccion\": \"Cra 55 #12sur-14\",\r\n    \"telefono\": \"3012345678\",\r\n    \"password\": \"123456!\",\r\n    \"estado\": true\r\n}")
    ).andExpect(status().isNotFound());
  }

  @Test
  public void updateClientByFields() throws Exception {
    mockMvc.perform(
        patch("/clientes/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n    \"edad\": 37,\r\n   \"estado\": false\r\n}")
    ).andExpect(status().isOk());
  }

  @Test
  public void createNewAccount() throws Exception {
    mockMvc.perform(
        post("/cuentas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n" +
                    "  \"numeroCuenta\": \"54200000562\",\r\n" +
                    "  \"tipoCuenta\": \"Ahorro\",\r\n" +
                    "  \"saldoInicial\": 100.5,\r\n" +
                    "  \"estado\":\"True\",\r\n" +
                    "  \"cliente\" : {\r\n" +
                    "      \"identificacion\":\"124578\"\r\n" +
                    "  }\r\n" +
                    "}")).andExpect(status().isCreated());
  }

  @Test
  public void createNewDepositTransaction() throws Exception {
    mockMvc.perform(
        post("/movimientos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\r\n" +
                    "  \"tipoMovimiento\": \"Deposito\",\r\n" +
                    "  \"valor\": 1000.5,\r\n" +
                    "  \"cuenta\" : {\r\n" +
                    "      \"numeroCuenta\":\"54200000562\"\r\n" +
                    "  }\r\n" +
                    "}")).andExpect(status().isCreated());
  }
}