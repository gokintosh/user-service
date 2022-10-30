package com.cracowdragons.userhack.controller;

import com.cracowdragons.userhack.model.TemporaryReport;
import com.cracowdragons.userhack.service.TemporaryDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: refactor this class
@RestController
@RequestMapping("/data")
public class TemporaryDeviceController {

    @Autowired
    TemporaryDeviceService temporaryDeviceService;

    public TemporaryDeviceController(TemporaryDeviceService temporaryDeviceService) {
        this.temporaryDeviceService = temporaryDeviceService;
    }

    @PostMapping("/save")
    public ResponseEntity postController(@RequestBody TemporaryReport temporaryReport) {

        temporaryDeviceService.log(temporaryReport);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
