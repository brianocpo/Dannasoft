<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="Generico Dannasoft v1.0">
        <meta name="author" content="Brian Montenegro">

        <title>Dannasoft - Login</title>
        <script src="${pageContext.request.contextPath}/resources/assets/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/js/jquery-1.12.4.js"></script>
        <script src="${pageContext.request.contextPath}/resources/assets/js/jquery.validate.min.js"></script> 
        <script src="${pageContext.request.contextPath}/resources/assets/js/messages_es.min.js"></script> 
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.min.css" />
        

        <!-- Latest compiled and minified JavaScript -->
        <script src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>
        <script type="text/javascript">
               $.validator.setDefaults( {
			submitHandler: function () {
				//alert( "submitted!" );
                                //formulario.resetForm();
			}
		} );

		$( document ).ready( function () {
			var formulario=$( "#signupForm" ).validate( {
				
				errorElement: "em",
				errorPlacement: function ( error, element ) {
					// Add the `help-block` class to the error element
					error.addClass( "help-block" );

					if ( element.prop( "type" ) === "checkbox" ) {
						error.insertAfter( element.parent( "label" ) );
					} else {
						error.insertAfter( element );
					}
				},
				highlight: function ( element, errorClass, validClass ) {
					$( element ).parents( ".col-sm-5" ).addClass( "has-error" ).removeClass( "has-success" );
				},
				unhighlight: function (element, errorClass, validClass) {
					$( element ).parents( ".col-sm-5" ).addClass( "has-success" ).removeClass( "has-error" );
				}
			} );

			
		});
	</script>
        </script>
        <style type="text/css">           
              #leftSide {
                background-color: #dbe3ff;
                height: 600px;
              }
              #mainContent {
                background-color: #fff5d4;
                height: 600px;
                padding-top: 20px;
              }
              .rsButtons {
                padding-top: 30px;
              }
              /* custom style for  validation method required. See ditails and classes name in developers tool */
              .error {
                color: red;
                font-size: 0.8em;
              }
        </style>
    </head>

    <body>

        <!-- I get this modal from getbootstrap.com -->

<!-- Button trigger modal -->

<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
 +
</button>

            <!-- Modal -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" style="width: 90%" >
                <div class="modal-content">
                  <!-- Zona Titulo Modal -->    
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Título</h4>
                  </div>
                  <!-- Cuerpo del Modal -->  
                    <div class="modal-body">
                        <div class="container-fluid">
                            <form id="signupForm" method="post" class="form-horizontal" action="">
                                
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="row" style="padding: 2px">
                                            <div class="col-md-4" style="text-align: right">
                                                <label for="firstname">First name</label>
                                            </div>
                                            <div class="col-sm-8">
						<input type="text" class="form-control" id="firstname" name="firstname" placeholder="First name" required minlength="2" />
					    </div>
                                        </div>
                                        
                                        <div class="row">
                                            <div class="col-md-4 right">
                                                <label for="lastname">Last name</label>
                                            </div>
                                            <div class="col-sm-8">
					    		<input type="text" class="form-control" id="lastname" name="lastname" placeholder="Last name" required />
					    </div>
                                        </div>
                                                       

							<div class="form-group">
								<label class="col-sm-4 control-label" for="lastname">Last name</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" id="lastname" name="lastname" placeholder="Last name" required />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label" for="username">Username</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" id="username" name="username" placeholder="Username" required/>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label" for="email">Email</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" id="email" name="email" placeholder="Email" required email="true" />
								</div>
							</div>
                                        
                                        
                                    </div>
                                    <div class="col-md-6">
                                                        <div class="form-group">
								<label class="col-sm-4 control-label" for="password">Password</label>
								<div class="col-sm-8">
									<input type="password" class="form-control" id="password" name="password" placeholder="Password" required />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label" for="confirm_password">Confirm password</label>
								<div class="col-sm-8">
									<input type="password" class="form-control" id="confirm_password" name="confirm_password" placeholder="Confirm password" required equalTo="#password" />
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-8 col-sm-offset-4">
									<div class="checkbox">
										<label>
											<input type="checkbox" id="agree" name="agree" value="agree" required />Please agree to our policy
										</label>
									</div>
								</div>
							</div>
                                        
                                    </div>
                                </div>
                                

                                <div class="modal-footer">
                                  <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                                  <button type="submit" class="btn btn-primary" name="signup" value="Guardar">Guardar</button>
                                </div>
			    </form> 
                      </div>
                        
                  </div>
                
                </div>
              </div>
            </div>
        
        
    </body>
</html>
