<%@ include file="top.jsp"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="main-container">
    <div class="main-container-signin">
        <form class="form-signin" action="../SignupServlet?version=web" method="POST">
            <h2> Please sign up</h2>
            <label class="sr-only">Name</label>
            <input name="name" class="form-control" type="text" autofocus="" required placeholder="Name"/>
            <label class="sr-only">Surname</label>
            <input name="surname" class="form-control" type="text" autofocus="" required placeholder="Surname"/>
            <div class="gender">
                <label>Gender: </label>
                <label class="radio-inline">
                <input name="gender" type="radio" value="M" required>M</label>
                <label class="radio-inline">
                <input name="gender" type="radio" value="F" required>F</label>
            </div>
            <label class="sr-only">Birth</label>
            <input name="birth" class="form-control" type="text" autofocus="" required placeholder="Birth date"/>
            <label class="sr-only">Email</label>
            <input name="email" class="form-control" type="text" autofocus="" required placeholder="Email"/>
            <label class="sr-only">Password</label>
            <input name="password" class="form-control" type="password" autofocus="" required placeholder="Password"/>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
        </form>
    </div>
</div>
<%@ include file="bottom.html"%>