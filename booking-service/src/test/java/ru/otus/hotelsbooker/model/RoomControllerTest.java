package ru.otus.hotelsbooker.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.dto.HotelDto;
import ru.otus.dto.RoomDto;
import ru.otus.hotelsbooker.repository.HotelJpaRepository;
import ru.otus.hotelsbooker.repository.RolesJpaRepository;
import ru.otus.hotelsbooker.repository.RoomJpaRepository;
import ru.otus.hotelsbooker.repository.UsersJpaRepository;
import ru.otus.hotelsbooker.service.HotelService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class RoomControllerTest {
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
    @Autowired
    private RoomJpaRepository roomJpaRepository;
    @Autowired
    private HotelJpaRepository hotelJpaRepository;
    private final int NOT_EXISTING_HOTEL_ID = 132;
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

        Room room = roomJpaRepository.findRoomById(roomDto.getId());
        Assertions.assertEquals(room.getName(), roomDto.getName());
        Assertions.assertEquals(room.getCapacity(), roomDto.getCapacity());

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
                .postForEntity("http://localhost:" + port + "/hotel/" + NOT_EXISTING_HOTEL_ID + "/room", entity, String.class);

        Assertions.assertNotNull(roomDtoResponseEntity);
        Assertions.assertEquals("Hotel with id=" + NOT_EXISTING_HOTEL_ID +" not found!", roomDtoResponseEntity.getBody());
//        Assertions.assertEquals(roomDtoResponseEntity.getStatusCode(), HttpStatusCode.valueOf(500));
//        RoomDto body = roomDtoResponseEntity.getBody();
//        Assertions.assertEquals(null, body.getName());
//        Assertions.assertEquals(0, body.getCapacity());
//        Assertions.assertEquals(null, body.getPriceByDay());
    }
}

