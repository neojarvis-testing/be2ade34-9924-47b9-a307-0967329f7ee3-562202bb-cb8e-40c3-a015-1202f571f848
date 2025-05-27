import React from "react";
function ErrorPage(){
    return(
        <div>
            <h2 data-testid="error-handling">Error</h2>
            <p data-testid="error-content">Oops! Something went wrong.Please try again later.</p>

        </div>
    );
}
export default ErrorPage;