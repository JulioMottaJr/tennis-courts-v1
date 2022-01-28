package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/tennis-court")
@Api(value = "API REST TennisCourt")

public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;


    @PostMapping
    @ApiOperation(value = "Add tennis court, please insert only a name for a tennis court EX: {\n" +
            "    \"name\":\"Lumiar\"\n" +
            "}")
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

   @GetMapping("{tennisCourtId}")
   @ApiOperation(value = "Find tennis court by ID")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @GetMapping("/schedule/{tennisCourtId}")
    @ApiOperation(value = "Find tennis court with schedules by ID")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
