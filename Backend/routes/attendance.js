const express=require('express');
const router=express.Router();
const {Student, validateID} = require('../models/student');

router.put('/attendance', async(req,res) =>{
    const { error } = validateID(req.body); 
    if (error) return res.json({status:'0', message: error.details[0].message});

    let student = await Student.findOne({id:req.body.id});
    if(!student) return res.json({status:'0', message:'Wrong Id entered'});

    console.log(student);

    student.attendance = student.attendance + 1;
    await student.save();

    res.json({status:'1',message:'Attendance Updated'});
});

module.exports=router;
