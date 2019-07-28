const express=require('express');
const router=express.Router();
const _ =require('lodash');
const {Student, validateID, validateUser, validateLogin} = require('../models/student');
const bcrypt=require('bcrypt');

router.post('/login', async (req,res) =>{
    const {error} = validateLogin(req.body);
    if (error) return res.json({status:'0', message: error.details[0].message});

    let user = await Student.findOne({ email: req.body.email });
    if (!user) return res.json({status:'0',message:'Invalid email or password.'});

    const validPassword = await bcrypt.compare(req.body.password, user.password);
    if (!validPassword) return res.json({status:0,message:'Invalid email or password.'})

    res.json({status:'1', message:'Login Success', name:user.name, email:user.email, rollNumber:user.rollNumber });
});

module.exports=router;