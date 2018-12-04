# Linguagens Formais e Autômatos - Trabalho Final
**Alunos:** Matheus Zanon e Rodrigo Silva

Simulador e Conversor de AFN para AFD.

---

## Compilando o Código Fonte

### Requisitos
* Java JDK 8
* Maven

### Instruções
Na ausência do arquivo executável com extensão `.jar`, o código fonte do simulador pode ser compilado ao executar o seguinte comando a partir do diretório raiz do projeto:
```
mvn clean install
```
O arquivo gerado poderá ser encontrado dentro da pasta `target`.


## Executando o Simulador

### Requisitos
* Java JRE 8

### Instruções
Para abrir o simulador, basta abrir o arquivo com extensão `.jar` com o Java dando um duplo clique ou executando o seguinte comando, substituindo `[nome do arquivo]` pelo nome do arquivo:
```
java -jar [nome do arquivo].jar
```

### Estrutura do arquivo de entrada

O formato do arquivo de entrada contendo a definição do AFN deve seguir o seguinte padrão: 
```
<M>=({<q0>,...,<qn>},{<s1>,...,<sn>},<ini>,{ <f0>,...,<fn>}) 
Prog 
(<q0>,<s1>)=<q1> 
... 
(<qn>,<sn>)=<q0>
```
onde: 
* *< M >*: nome dado ao autômato; 
* *< qi >*: para  0 ≤ i ≤ n,  com n ∈ N e n ≥ 0, representa um estado do autômato; 
* *< si >*: para 1 ≤ i ≤ n, com n ∈ N e n ≥ 1, representa um símbolo do alfabeto da linguagem reconhecida pelo autômato; 
* *< ini >*: indica o estado inicial do autômato, sendo que ini é um estado do autômato; 
* *< fi >*: para 0 ≤ i ≤ n, com n ∈ N e n ≥ 0, representa um estado final do autômato, sendo que fi é um estado do autômato; 
* *(< qi >,< si >) =< qj >*: descreve a função programa aplicada a um estado qi e um símbolo de entrada si que leva a computação a um estado qj.
#### Exemplo:
```
AUTÔMATO=({q0,q1,q2,q3},{a,b},q0,{q1,q3}) 
Prog 
(q0,a)=q1 
(q0,b)=q2 
(q1,b)=q2 
(q2,a)=q3 
(q2,a)=q2
(q3,a)=q3 
(q3,b)=q2
```
