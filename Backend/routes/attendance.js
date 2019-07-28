const express=require('express');
const router=express.Router();
const {Student, validateID} = require('../models/student');

router.put('/attendance', async(req,res) =>{
    const { error } = validateID(req.body); 
    if (error) return res.json({status:'0', message: error.details[0].message});

    let student = await Student.findOne({id:req.body.id});
    if(!student) return res.json({status:'0', message:'Wrong Id entered'});

    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); 
    var yyyy = today.getFullYear();

    today = dd + '/' + mm + '/' + yyyy;

    if(student.attendance.length >=0)
    {
        var last = student.attendance[student.attendance.length - 1];
        if(today==last){
            return res.json({status:'0',message:'Two attendance caanot be marked on a single day'})
        }
    }

    student.attendance.push(today);
    await student.save();

    res.json({status:'1',message:'Attendance Updated'});
});

module.exports=router;
