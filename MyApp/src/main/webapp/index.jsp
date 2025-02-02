<html>
 <head>
  <title>
   Login
  </title>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&amp;display=swap" rel="stylesheet"/>
  <style>
   body, html {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            font-family: 'Roboto', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #0b0e14;
        }
        .container {
            width: 70%;
            max-width: 350px;
            padding: 50px;
            background-color: #1a1d2e;
            border-radius: 10px;
            color: #fff;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .container h1 {
            margin: 0;
            font-size: 28px;
            font-weight: 700;
        }
        .container form {
            width: 100%;
            margin-top: 30px;
            display: flex;
            flex-direction: column;
        }
        .container form input {
            width: 100%;
            padding: 15px;
            margin-bottom: 20px;
            border: none;
            border-radius: 5px;
            background-color: #2a2d3e;
            color: #fff;
            font-size: 16px;
        }
        .container form input::placeholder {
            color: #6c757d;
        }
        .container form button {
            padding: 15px;
            background-color: #00aaff;
            border: none;
            border-radius: 5px;
            color: #fff;
            font-size: 18px;
            cursor: pointer;
            margin-bottom: 10px;
        }
        .container form button:last-child {
            background-color: #6c757d;
        }
  </style>
 </head>
 <body>
  <div class="container">
   <h1>
    Login
   </h1>
   <form action="login" method="post">
    <input name="username" placeholder="Username" required="" type="text"/>
    <input name="password" placeholder="Password" required="" type="password"/>
    <button name="action" type="submit" value="login">
     Login
    </button>
    <button name="action" type="submit" value="showall">
     Show Records
    </button>
   </form>
  </div>
 </body>
</html>