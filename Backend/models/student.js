const Joi = require('joi');
const mongoose = require('mongoose');

const menuSchema = new mongoose.Schema({
  id: {
    type: Number,
    required: true
  },
  name:String,
  rollNumber:Number,
  password:Number,
  attendance:Number
});


const Student = mongoose.model('Student', menuSchema);

function validateID(student) {
  const schema = {
    id: Joi.number().required()
  };
  return Joi.validate(student, schema);
}

function validateUser(user) {
  const schema = {
    name: Joi.string().min(5).max(50).required(),
    email: Joi.string().min(5).max(255).required().email(),
    password: Joi.string().min(5).max(255).required(),
    rollNumber: Joi.string().required()
  };
  return Joi.validate(user, schema);
}

function validateLogin(req) {
  const schema = {
    email: Joi.string().min(5).max(255).required().email(),
    password: Joi.string().min(5).max(255).required()
  };
  return Joi.validate(req, schema);
}

exports.Student = Student; 
exports.validateID = validateID;
exports.validateUser = validateUser;
exports.validateLogin = validateLogin;