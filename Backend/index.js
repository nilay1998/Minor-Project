const express = require('express');
const app = express();
const bodyParser=require('body-parser');
const registerId=require('./routes/register_Id');
const registerUser=require('./routes/register_user');
const login=require('./routes/login')
const mongoose=require('mongoose');

mongoose.connect('mongodb://localhost/Project_Minor')
  .then(() => console.log('Connected to MongoDB...'))
  .catch(err => console.error('Could not connect to MongoDB...'));

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:true}));
app.use('/api',registerId);
app.use('/api',registerUser);
app.use('/api',login);

app.get('/api/get',async(req,res)=>{
    res.json('RUNNING');
});

const port = process.env.PORT || 3000;
app.listen(port, () => console.log(`Listening on port ${port}...`));