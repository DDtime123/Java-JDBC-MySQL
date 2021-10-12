package com.jdbc.controller;

import com.jdbc.models.GenericResponse;
import com.jdbc.models.StuModel;
import com.jdbc.repository.StuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SampleController {
    @Autowired
    private StuRepository stuRepo;

    @RequestMapping(value="/public/addStu", method= RequestMethod.POST)
    public ResponseEntity<GenericResponse> addStu(
            @RequestBody
                    StuModel stuToAdd
    )
    {
        GenericResponse retMsg = new GenericResponse();
        if(stuToAdd != null)
        {
            try
            {
                stuRepo.addStu(stuToAdd);

                retMsg.setSuccess(true);
                retMsg.setStatusMsg("Operation is successful.");
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                retMsg.setSuccess(false);
                retMsg.setStatusMsg("Exception occurred.");
            }
        }
        else
        {
            retMsg.setSuccess(false);
            retMsg.setStatusMsg("No valid stu model object to be added");
        }

        ResponseEntity<GenericResponse> retVal;
        retVal = ResponseEntity.ok(retMsg);
        return retVal;
    }

    @RequestMapping(value="/public/getStus", method=RequestMethod.GET)
    public ResponseEntity<StuModel> getStus(
            @RequestParam("id")
            String id)
    {
        StuModel foundStus = stuRepo.findStu(id);

        if(foundStus == null){
            foundStus = new StuModel();
        }

        ResponseEntity<StuModel> retVal;
        retVal = ResponseEntity.ok(foundStus);
        return retVal;
    }


}
