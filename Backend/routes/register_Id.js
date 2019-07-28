const express=require('express');
const router=express.Router();
const _ =require('lodash');
const {Student, validate} = require('../models/student');
//const bcrypt=require('bcrypt');

router.get('/', async (req,res) =>{
    const student =await Student.find();
    if(student) return res.json({status:'1', message:'success' ,allItems: student });
});

router.post('/id', async (req,res) => {
    const { error } = validate(req.body); 
    if (error) return res.json({status:'0', message: error.details[0].message});

    const user = new Student(_.pick(req.body, ['id']));
    await user.save();

    res.json({status:'1',message:'Registeration Success'});
});

module.exports=router;