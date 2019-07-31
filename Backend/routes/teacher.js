const express=require('express');
const router=express.Router();
const _ =require('lodash');
const {Faculty, validateUser, validateLogin} = require('../models/teacher');
const bcrypt=require('bcrypt');

router.get('/getf', async (req,res) =>{
    const faculty =await Faculty.findOne();
    if(faculty) return res.json({status:'1', message:'success' , isClass: faculty.isClass });
});

router.post('/faculty', async (req,res) => {
    const { error } = validateUser(req.body); 
    if (error) return res.json({status:'0', message: error.details[0].message});

    let faculty = await Faculty.findOne({ email: req.body.email });
    if (faculty) return res.json({status:'0',message:'User already registered.'});

    faculty = new Faculty(_.pick(req.body, ['name', 'email', 'password']));
    const salt = await bcrypt.genSalt(10);
    faculty.password = await bcrypt.hash(faculty.password, salt);
    await faculty.save();

    res.json({status:'1',message:'Registeration Success'});
});

router.post('/loginf', async (req,res) =>{
    const {error} = validateLogin(req.body);
    if (error) return res.json({status:'0', message: error.details[0].message});

    let faculty = await Faculty.findOne({ email: req.body.email });
    if (!faculty) return res.json({status:'0',message:'Invalid email or password.'});

    const validPassword = await bcrypt.compare(req.body.password, faculty.password);
    if (!validPassword) return res.json({status:0,message:'Invalid email or password.'})

    res.json({status:'1', message:'Login Success', name: faculty.name, email: faculty.email});
});

module.exports=router;

