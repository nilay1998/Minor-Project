const express=require('express');
const router=express.Router();
const _ =require('lodash');
const {Student, validateID, validateUser, validateLogin} = require('../models/student');
const bcrypt=require('bcrypt');

router.post('/user', async(req,res) =>{
    const { error } = validateUser(req.body); 
    if (error) return res.json({status:'0', message: error.details[0].message});

    let student = await Student.findOne({ email: req.body.email });
    if (student) return res.json({status:'0',message:'Student already registered.'});

    student = new Student(_.pick(req.body, ['name', 'email', 'password','rollNumber']));
    const salt = await bcrypt.genSalt(10);
    student.password = await bcrypt.hash(student.password, salt);
    await student.save();

    res.json({status:'1',message:'Registeration Success'});
});