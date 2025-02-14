package med.voll.api.infra.security;

public class AutenticationPorPerfilUsuario {

 /*
  COntrole de acesso por url
  implementacao do metodo de forma que o filtro seja feito por papeis de usuarios
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/pacientes").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



    Controle de acesso por anotações
    Outra maneira de restringir o acesso a determinadas funcionalidades, com base no perfil dos usuários,
    é com a utilização de um recurso do Spring Security conhecido como Method Security, que funciona com a utilização de anotações em métodos:

        @GetMapping("/{id}")
        @Secured("ROLE_ADMIN")
      public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
       }

       No exemplo de código anterior o metodo foi anotado com @Secured("ROLE_ADMIN"),
       para que apenas usuários com o perfil ADMIN possam disparar requisições para detalhar um medico.
       A anotação @Secured pode ser adicionada em métodos individuais ou mesmo na classe, que seria o equivalente a adicioná-la em todos os métodos.

    Atenção! Por padrão esse recurso vem desabilitado no spring Security, sendo que para o
    utilizar devemos adicionar a seguinte anotação na classe Securityconfigurations do projeto: @EnableMethodSecurity(securedEnabled = true)


  */


}
