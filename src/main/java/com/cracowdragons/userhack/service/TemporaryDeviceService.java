package com.cracowdragons.userhack.service;

import com.cracowdragons.userhack.model.TemporaryReport;
import org.springframework.stereotype.Service;

//TODO: refactor this class
@Service
public class TemporaryDeviceService {

    public void log(TemporaryReport temporaryReport) {

        System.out.println(temporaryReport);
    }
}
