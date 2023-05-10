package ru.otus.telegram_bot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.dto.HotelDto;
import ru.otus.dto.LocalRoomDto;
import ru.otus.dto.RoomDto;

import java.util.List;

@FeignClient(url = "localhost:8881/hotel", name = "hotel-client", configuration = FeignBasicAuthInterceptor.class)
public interface HotelClient {
    /**
     * GET localhost:8881/hotel?city=something
     *
     * @param city
     * @return
     */
    // todo: change return type to HotelDto
    @GetMapping
    List<HotelDto> getAllHotels(@RequestParam(name = "city", required = false) String city);

    /**
     * GET localhost:8881/hotel/{id}
     * <p>
     * GET localhost:8881/hotel/1
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    HotelDto getHotelById(@PathVariable Long id);


    /**
     * POST localhost:8881/hotel/{id}/room
     * * body {}
     *
     * @param roomDto
     * @param id
     * @return
     */
    @PostMapping(path = "/{id}/room", consumes = "application/json")
    RoomDto addRoom(@RequestBody RoomDto roomDto, @PathVariable Long id);


    @PutMapping(path = "/0/room/0/localroom/{localRoomId}/disable")
    void disableLocalRoom(@PathVariable Long localRoomId);

    /**
     * POST localhost:8881/hotel
     * body {}
     *
     * @param hotel
     * @return
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    HotelDto createHotel(@RequestBody HotelDto hotel);
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    HotelDto updateHotel(@PathVariable Long id, @RequestBody HotelDto hotel);
    @PostMapping(path = "/0/room/{roomId}/localroom", consumes = "application/json", produces = "application/json")
     LocalRoomDto addLocalRoom(@RequestBody LocalRoomDto localRoomDto, @PathVariable Long roomId);
}
