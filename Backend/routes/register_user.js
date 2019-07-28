const express=require('express');
const router=express.Router();
const _ =require('lodash');
const {Student, validateID, validateUser, validateLogin} = require('../models/student');
const bcrypt=require('bcrypt');

router.put('/user', async(req,res) =>{
    const { error } = validateUser(req.body); 
    if (error) return res.json({status:'0', message: error.details[0].message});

    let student = await Student.findOne({ email: req.body.email });
    if (student) return res.json({status:'0',message:'Student already registered.'});

    student = await Student.findOne({ id:req.body.id});
    if(!student || student.email) return res.json({status:'0', message:'Wrong Id entered'});

    const salt = await bcrypt.genSalt(10);
    req.body.password = await bcrypt.hash(req.body.password, salt);

    student = await Student.findOneAndUpdate({id:req.body.id},(_.pick(req.body, ['name', 'email', 'password','rollNumber'])));
    

    res.json({status:'1',message:'Registeration Success'});
});

module.exports=router;