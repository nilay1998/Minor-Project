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
  attendance:Number,
  isClass:Boolean
});


const Student = mongoose.model('Student', menuSchema);

function validateUser(student) {
  const schema = {
    id: Joi.number().required()
  };

  return Joi.validate(student, schema);
}

exports.Student = Student; 
exports.validate = validateUser;