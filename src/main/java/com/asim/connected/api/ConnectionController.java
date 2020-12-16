package com.asim.connected.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asim.connected.service.ConnectionServices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@RequestMapping("/connected")
@EnableSwagger2
@Api(value = "city-connected application", description = "Find if a path exists between two cities")
public class ConnectionController {

	Logger logger = LoggerFactory.getLogger(ConnectionController.class);

	@Autowired
	ConnectionServices connectionServices;

	@ApiOperation(value = "Find if a path exists between two cities",
            notes = "Returns true if cites connected and false otherwise ",
            response = String.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Either destination or origin city does not exist or invalid", response = NullPointerException.class),
            @ApiResponse(code = 500, message = "Generic error", response = Exception.class)
    })
	@GetMapping
	public ResponseEntity<?> getConnectedStatus(
			@ApiParam(name = "origin", value = "Origin City name", required = true) @RequestParam(name = "origin", required = true) String origin,
			@ApiParam(name = "destination", value = "Destination City name", required = true) @RequestParam(name = "destination", required = true) String destination) {
		
		/* Requested cities status , cities are connected or not */
		String status = connectionServices.checkCitiesConnection(origin, destination);
		logger.info("Origin city {} , Destination city {} are connected with each other : {} ", origin, destination,
				status);
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}

}
