package ru.otus.hotelsbooker.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dto.HotelDto;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.repository.RolesJpaRepository;
import ru.otus.hotelsbooker.repository.UsersJpaRepository;
import ru.otus.hotelsbooker.service.HotelService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class HotelControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RolesJpaRepository rolesJpaRepository;
    @Autowired
    private UsersJpaRepository usersJpaRepository;
    @BeforeEach
    public void prepare(){
        Role roleUser = Role.builder()
                .name("ROLE_USER")
                .build();
        Role roleHotel = Role.builder()
                .name("ROLE_HOTEL")
                .build();
        rolesJpaRepository.saveAll(List.of(roleUser,roleHotel));
        User user = User.builder()
                .username("user")
                .password("$2a$10$9VyipY09UB19OCWeUG0Ciu5SMFs0y2/Xco/J8uARQTN0bgh8pSU3i")
                .roles(Set.of(roleUser, roleHotel))
                .build();
        usersJpaRepository.save(user);
    }
    @AfterEach
    public void clear(){
        List<User> users = usersJpaRepository.findAll();
        users.forEach(user -> usersJpaRepository.delete(user));
    }

    /**
     * <h1>Тестирование API добавления отелей</h1>
     * <ul>
     *     <li>Создать отель (город=Москва, название=Hilton)</li>
     *     <li>Создать отель (город=Москва, название=Hilton)</li>
     * </ul>
     */
    @Test
    @DisplayName("Тестирование API добавления отелей")
    public void testCreateNewHotelRestTemplate() throws Exception {
        List<RoomDto> rooms = new ArrayList<>();
        HotelDto hotel = new HotelDto
                (1L, "Hilton", "Moscow", "Russia", "Red Square building 1", 8.0, rooms);
        String hotelJson = objectMapper.writeValueAsString(hotel);
        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("user", "user");
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(hotelJson, headers);
        // способ 1
        ResponseEntity<HotelDto> hotelDtoResponseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/hotel", entity, HotelDto.class);

        Assertions.assertEquals(HttpStatusCode.valueOf(200), hotelDtoResponseEntity.getStatusCode());
        HotelDto body = hotelDtoResponseEntity.getBody();
        Assertions.assertEquals(hotel.getName(), body.getName());
        Assertions.assertEquals(hotel.getCity(), body.getCity());
        Assertions.assertEquals(hotel.getCountry(), body.getCountry());
    }

    /**
     * <h1>Тестирование API добавления отелей</h1>
     * <ul>
     *     <li>Создать отель (город=Москва, название=Hilton)</li>
     *     <li>Создать отель (город=Москва, название=Hilton)</li>
     * </ul>
     */
    @Test
    @DisplayName("Тестирование API добавления отелей")
    public void testCreateNewHotelMockMvc() throws Exception {
        HotelDto hotel = new HotelDto("Hilton", "Moscow", "Russia", "Red Square building 1");
        String hotelJson = objectMapper.writeValueAsString(hotel);
        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("user", "user");
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        // способ 2
        MvcResult mvcResult = mockMvc.perform(
                        post("/hotel")
                                .headers(headers)
                                .content(hotelJson.getBytes()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        HotelDto body2 = objectMapper.readValue(contentAsString, HotelDto.class);
        Assertions.assertEquals(hotel.getName(), body2.getName());
        Assertions.assertEquals(hotel.getCity(), body2.getCity());
        Assertions.assertEquals(hotel.getCountry(), body2.getCountry());
    }

    @Test
    @DisplayName("Тестирование API успешного добавления номера в отель")
    public void testCreateNewRoomSuccessfullyMockMvc() throws Exception {
        HotelDto hotelDto = new HotelDto("Hilton", "Moscow", "Russia", "Red Square building 1");
        hotelDto = hotelService.createNewHotel(hotelDto);
        RoomDto roomDto = new RoomDto(1L, "single", 1, new BigDecimal(100));

        String roomJson = objectMapper.writeValueAsString(roomDto);
        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("user", "user");
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        Long id = hotelDto.getId();
        // способ 2
        MvcResult mvcResult = mockMvc.perform(
                        post("/hotel/" + id + "/room")
                                .headers(headers)
                                .content(roomJson.getBytes()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        RoomDto body2 = objectMapper.readValue(contentAsString, RoomDto.class);
        Assertions.assertEquals(roomDto.getName(), body2.getName());
        Assertions.assertEquals(roomDto.getCapacity(), body2.getCapacity());
        Assertions.assertEquals(roomDto.getPriceByDay(), body2.getPriceByDay());
    }

    @Test
    @DisplayName("Тестирование API неуспешного добавления апартаментов в отель")
    public void testCreateNewRoomNotSuccessfullyMockMvc() throws Exception {
        HotelDto hotelDto = new HotelDto("Hilton", "Moscow", "Russia", "Red Square building 1");
        hotelDto = hotelService.createNewHotel(hotelDto);
        RoomDto roomDto = new RoomDto(1L, "single", 1, new BigDecimal(100));

        String roomJson = objectMapper.writeValueAsString(roomDto);
        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("user", "user");
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        Long id = hotelDto.getId();
        // способ 2
        HttpEntity<String> entity = new HttpEntity<>(roomJson, headers);
        ResponseEntity<String> roomDtoResponseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/hotel/132/room", entity, String.class);

        Assertions.assertNotNull(roomDtoResponseEntity);
        Assertions.assertEquals("Hotel with id=132 not found!", roomDtoResponseEntity.getBody());
//        Assertions.assertEquals(roomDtoResponseEntity.getStatusCode(), HttpStatusCode.valueOf(500));
//        RoomDto body = roomDtoResponseEntity.getBody();
//        Assertions.assertEquals(null, body.getName());
//        Assertions.assertEquals(0, body.getCapacity());
//        Assertions.assertEquals(null, body.getPriceByDay());
    }
}
