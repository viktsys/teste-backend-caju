# Teste Backend da Caju
---

API para autorização de operações do cartão multi-benefícios da Caju.

## Requisitos
- Implementar toda a estrutura necessária (modelagem, rotas, etc).
- Implementar a regra de negócio de autorização de operações. (Autorizador Dependendo do comeciante, Simples e Fallback)
- Implementar testes unitários para as rotas.

## Tecnologias escolhidas:
- Java 21 (GraalVM JDK)
- SpringBoot 3.3.2
- Hibernate 
- JUnit
- SpringDoc OpenAPI (Swagger)
- Postgres 16 (usando Docker)

## Como rodar o projeto?
1. Clone o repositório
2. Execute o comando `docker-compose up` para subir o banco de dados.
3. Execute o comando `gradle bootRun` para rodar o projeto.
4. Se tudo ocorrer bem, a aplicação estará rodando em `http://localhost:8080`
5. Se quiser uma interface mais amigável, você pode usar o Swagger, que estará disponível em `http://localhost:8080/swagger-ui.html`

## Observações importantes
Visto que o foco principal no teste foi dado na implementação da regra de negócio e sua escalabilidade, algumas coisas não foram implementadas:
- Implementação de cache para os modelos usados nas regras de autorização de transação
- Implementação de cache para as consultas a contas

## Solução para a questão aberta (L4)

```
Transações simultâneas: dado que o mesmo cartão de crédito pode ser utilizado em diferentes serviços online, existe uma pequena mas existente probabilidade de ocorrerem duas transações ao mesmo tempo. O que você faria para garantir que apenas uma transação por conta fosse processada em um determinado momento? Esteja ciente do fato de que todas as solicitações de transação são síncronas e devem ser processadas rapidamente (menos de 100 ms), ou a transação atingirá o timeout.
```

Vamos entender o problema de porquê as transações não podem ser processadas simultaneamente. O problema vem do fato de que, se duas transações forem processadas ao mesmo tempo, elas podem acabar gastando mais do que o limite da conta, o que é um problema visto que o cartão de beneficios é atrelado a uma conta de pagamento cujo o saldo não pode ser negativo. (BACEN agradece a gente seguir as regras =P).

![Exemplificação do problema](docs/caju_problema_l4_sincronia.png)

Esse é um problema classico de sincronia que tratamos em sistemas tanto distribuidos, concorrentes e com necessidades ACID (Atomicidade, Consistência, Isolamento e Durabilidade). Para resolver esse problema temos algumas abordagens:

### Usar um banco de dados que possua suporte a transações
Bancos de dados SQL como o Postgres, MySQL, Oracle, etc possuem suporte a transações e são compativeis com os requisitos ACID.
Basicamente, ao executarmos uma operação sobre algum dados, uma trava é ativada que impede qualquer leitura ou escrita sobre os dados que estão sendo manipulados por uma transação. Traduzindo para o nosso problema, quando iniciamos a operação de débito sobre a conta e criação do registro da transação sobre o banco, o banco de dados irá travar a conta, alterar seu saldo disponível, registrar a transação (compra) e liberar as travas para as próximas operações. Essa trava é a nível de linha do banco de dados, ou seja, não interfere com outras contas que estão sendo manipuladas em outras transações.

`PROBLEMA:` O BD toma as decisões de travamento, liberação de travas e adiamento de transações caso necessárias, o que pode ser um problema de performance em sistemas com muitas transações simultâneas sobre os mesmos dados. Embora seja uma solução robusta, ela pode não ser a mais performative considerando o tempo de resposta de 100ms embora, no geral, bancos de dados não demora mais que uma dezena de milliseconds para realizar uma operação.

### Cache para tudo que pudermos
Uma outra estratégia é ganhar tempo nas operações acessórias a transação, como por exemplo, a consulta do saldo da conta. Se o saldo da conta for consultado frequentemente, podemos armazenar esse saldo em um cache (como o Redis) e atualizar esse cache sempre que uma transação for realizada. Dessa forma, a consulta do saldo da conta será extremamente rápida e não irá interferir no tempo. Essa mesma tatíca pode ser utilizada quanto a dados como consulta na tabela por MCC e nome do comerciante para autorização da transação.

`PROBLEMA:` O cache fará com que ganhemos tempo de resposta, mas não garante que a operação será realizada em tempo
constante e nem que ainda conseguiremos alcançar o objetivo de menos que 100ms.

### Segregar os saldos da conta para um banco NoSQL de alta performance (Redis)
Uma outra abordagem seria a de usar um banco de dados NoSQL como o Redis para armazenar os saldos das contas. O Redis é um banco de dados em memória que é extremamente rápido e pode ser usado para armazenar dados que são acessados frequentemente e que precisam de uma resposta rápida. Tomando Redis como exemplo, toda operação realizada possui um nível de complexidade previsível e constante, o que garante que a operação seja realizada em tempo constante, independente da quantidade de dados armazenados.
Por exemplo, para realizar uma operação de subtração sobre uma chave, a operação para isso `DECRBY` possui tempo constante de O(1) (ou seja, extremamente rápido). E graças a implementação do Redis, a transação cumpre o request de ser atômica e consistente.

`PROBLEMA:` O Redis é um banco de dados em memória por natureza, por conta disso, há alternativas hoje em dia com armazenamento em disco com compatibilidade total com o protocolo com penalidade de performance relativamente baixa (isso considerando dezenas de milhares de transações sobre uma mesma chave). Há também as questões tecnicas relativas ao desenvolvimento, se torna mais complicado gerir um banco de dados NoSQL e um banco de dados SQL ao mesmo tempo, isso aumenta a complexidade do sistema e torna maiores os riscos de falhas caso não seja tratado corretamente.
 
### Solução final?
Uma mistura de todos, durante a minha resposta discorri sobre as vantagens e desvantagens de cada uma das soluções, mas a solução final seria uma mistura de todas elas. Usar um banco de dados SQL para armazenar o saldo de forma ACID e garantir a consistência dos dados, usar um cache para armazenar os dados acessórios (MCC e Merchant Names) utilizados para autorização da transação e usar um banco de dados NoSQL no modelo KV para armazenar os saldos das contas e garantir a performance das operações de consulta e alteração em situações de alta demanda independente de quão átipica seja.
