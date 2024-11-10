<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tag"%>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>E-Commerce - Finalizar Compra</title>
    <link rel="icon" type="image/x-icon" href="assets/img/favicon.ico" />
    <!-- Font Awesome icons (free version)-->
    <script src="https://use.fontawesome.com/releases/v5.15.1/js/all.js" crossorigin="anonymous"></script>
    <!-- Google fonts-->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
    <link href="https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic" rel="stylesheet"
        type="text/css" />
    <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="css/styles.css" rel="stylesheet" />
</head>

<body id="page-top">
    <!-- Navigation-->
    <tag:menu-superior></tag:menu-superior>

    <!-- Masthead-->
    <header class="masthead" id="login">
        <div class="container">
            <h2 class="text-uppercase text-white">Finalizar Compra</h2>
        </div>
    </header>

    <!-- Formulário de finalização de compra-->
    <section class="page-section bg-dark text-white" id="portfolio">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h3 class="section-subheading text-muted">Insira ou revise os dados para finalizar sua compra</h3>
                </div>
            </div>

            <!-- Formulário de dados do cliente -->
            <form method="post" action="finalizar/enviarDados">
                <div class="form-group">
		            <label for="nome">Nome</label>
		            <input type="text" class="form-control" id="nome" name="nome" placeholder="Nome completo" required>
		        </div>

		        <div class="form-group">
		            <label for="email">Email</label>
		            <input type="email" class="form-control" id="email" name="email" placeholder="Seu email" required>
		        </div>

		        <div class="form-group">
		            <label for="numero">Telefone</label>
		            <input type="text" class="form-control" id="numero" name="numero" placeholder="Telefone" required>
		        </div>

                <!-- Tipo de Documento (CPF/CNPJ) -->
                <div class="form-group">
                    <label for="tipoDocumento">Tipo de Documento</label>
                    <select class="form-control" id="tipoDocumento" name="tipoDocumento">
                    	<option value="">Selecione o tipo de documento</option>
                        <option value="cpf">CPF</option>
                        <option value="cnpj">CNPJ</option>
                    </select>
                </div>

                <!-- Campo CPF/CNPJ com máscara dinâmica -->
		        <div class="form-group">
		            <label for="cpfCnpj">CPF/CNPJ</label>
		            <input type="text" class="form-control" id="cpfCnpj" name="cpfCnpj" placeholder="CPF ou CNPJ" required>
		        </div> 

		        <div class="form-group">
		            <label for="endereco">Endereço</label>
		            <input type="text" class="form-control" id="endereco" name="endereco" placeholder="Nome da rua" required>
		        </div>

		        <div class="form-group">
		            <label for="enderecoN">Número</label>
		            <input type="text" class="form-control" id="enderecoN" name="enderecoN" placeholder="Número da residência" required>
		        </div>

		        <div class="form-group">
		            <label for="cep">CEP</label>
		            <input type="text" class="form-control" id="cep" name="cep" placeholder="CEP" required>
		        </div>

		        <div class="form-group">
		            <label for="bairro">Bairro</label>
		            <input type="text" class="form-control" id="bairro" name="bairro" placeholder="Bairro" required>
		        </div>

		        <div class="form-group">
		            <label for="cidade">Cidade</label>
		            <input type="text" class="form-control" id="cidade" name="cidade" placeholder="Cidade" required>
		        </div>
		        
                <div class="form-group">
                    <label for="estado">Estado</label>
                    <select name="estado" id="estado" class="form-control" required>
					    <option value="">Selecione o estado</option>
					    <option value="AC">Acre (AC)</option>
					    <option value="AL">Alagoas (AL)</option>
					    <option value="AP">Amapá (AP)</option>
					    <option value="AM">Amazonas (AM)</option>
					    <option value="BA">Bahia (BA)</option>
					    <option value="CE">Ceará (CE)</option>
					    <option value="DF">Distrito Federal (DF)</option>
					    <option value="ES">Espírito Santo (ES)</option>
					    <option value="GO">Goiás (GO)</option>
					    <option value="MA">Maranhão (MA)</option>
					    <option value="MT">Mato Grosso (MT)</option>
					    <option value="MS">Mato Grosso do Sul (MS)</option>
					    <option value="MG">Minas Gerais (MG)</option>
					    <option value="PA">Pará (PA)</option>
					    <option value="PB">Paraíba (PB)</option>
					    <option value="PR">Paraná (PR)</option>
					    <option value="PE">Pernambuco (PE)</option>
					    <option value="PI">Piauí (PI)</option>
					    <option value="RJ">Rio de Janeiro (RJ)</option>
					    <option value="RN">Rio Grande do Norte (RN)</option>
					    <option value="RS">Rio Grande do Sul (RS)</option>
					    <option value="RO">Rondônia (RO)</option>
					    <option value="RR">Roraima (RR)</option>
					    <option value="SC">Santa Catarina (SC)</option>
					    <option value="SP">São Paulo (SP)</option>
					    <option value="SE">Sergipe (SE)</option>
					    <option value="TO">Tocantins (TO)</option>
					</select>
                </div>

                <!-- Botão de Finalizar Compra -->
                <div class="row mt-4">
                    <div class="col-md-12 text-right">
                        <button type="submit" class="btn btn-success btn-lg">Finalizar Compra</button>
                    </div>
                </div>
            </form>
        </div>
    </section>

    <tag:footer></tag:footer>

    <!-- jQuery e jQuery Mask -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>

    <!-- Scripts para aplicar máscaras de acordo com a seleção -->
    <script>
        $(document).ready(function () {
            // Máscara para o telefone
            var options = {
			  onKeyPress: function(cep, e, field, options) {
			    var masks = ['(00) 0000-00000', '(00) 00000-0000'];
			    var mask = (cep.length > 14) ? masks[1] : masks[0];
			    $('#numero').mask(mask, options);
			  }
			};
			
			$('#numero').mask('(00) 0000-00000', options);
            // Máscara para o CEP
            $('#cep').mask('00000-000');

            // Troca a máscara de acordo com o tipo de documento
            $('#tipoDocumento').change(function () {
                var tipo = $(this).val();
                if (tipo === 'cpf') {
                    $('#cpfCnpj').mask('000.000.000-00');
                } else if (tipo === 'cnpj') {
                    $('#cpfCnpj').mask('00.000.000/0000-00');
                }
            });

            // Aplica a máscara inicial como CPF
            $('#cpfCnpj').mask('000.000.000-00');
        });
    </script>

    <!-- Core theme JS-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
