document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent form submission

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const errorMessage = document.getElementById("error-message");

    // Simple validation for demonstration purposes
    if (username === "user" && password === "pass") {
        alert("Login successful!");
        // Redirect or perform further actions
    } else {
        errorMessage.textContent = "Invalid username or password.";
    }
});
