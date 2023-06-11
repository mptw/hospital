package com.hospital.app.Hospital.Controller.View;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hospital.app.Hospital.Model.Room;
import com.hospital.app.Hospital.Model.RoomType;
import com.hospital.app.Hospital.Service.RoomService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/ui/rooms")
public class RoomViewController {

	@Autowired
	private RoomService roomService;

	@GetMapping(value = "/{id}")
	public String getRoom(@PathVariable int id, Model model) {
		if (roomService.checkPermissions()) {
			Room room = roomService.get(id);
			model.addAttribute("rooms", Collections.singleton(room));
			return "/rooms/rooms-list";
		} else
			return "unauthorized";
	}

	@SuppressWarnings("unchecked")
	@GetMapping
	public String getRooms(Model model) {
		if (roomService.checkPermissions()) {
			List<Room> rooms = (List<Room>) roomService.getRooms(0, 10).getContent();
			model.addAttribute("rooms", rooms);
			return "/rooms/rooms-list";
		} else
			return "unauthorized";
	}

	@GetMapping(value = "/{id}/edit")
	public String editRoomForm(@PathVariable int id, Model model) {
		if (roomService.checkPermissions()) {
			Room roomToUpdate = roomService.get(id);
			model.addAttribute("room", roomToUpdate);
			model.addAttribute("types", Arrays.asList(RoomType.values()));
			return "/rooms/rooms-edit";
		} else
			return "unauthorized";
	}

	@PostMapping(value = "/{id}/edit")
	public String updateRoom(@PathVariable("id") int id, @Valid @ModelAttribute("room") Room room, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("room", room);
			return "/rooms/rooms-edit";
		}
		roomService.update(id, room);
		return "redirect:/ui/rooms";
	}

	@GetMapping(value = "/create")
	public String crateRoomForm(Model model) {
		if (roomService.checkPermissions()) {
			Room room = new Room();
			model.addAttribute("room", room);
			model.addAttribute("types", Arrays.asList(RoomType.values()));
			return "/rooms/rooms-create";
		} else
			return "unauthorized";
	}

	@PostMapping(value = "/create")
	public String saveRoom(@Valid @ModelAttribute("room") Room room, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("room", room);
			return "/rooms/rooms-create";
		}
		roomService.create(room);
		return "redirect:/ui/rooms";
	}

	@GetMapping(value = "/{id}/delete")
	public String deleteRoomForm(@PathVariable int id, Model model) {
		if (roomService.checkPermissions()) {
			roomService.delete(id);
			return "redirect:/ui/rooms";
		} else
			return "unauthorized";
	}

}
