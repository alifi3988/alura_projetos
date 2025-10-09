-- CRIAÇÃO DA TABELA "PRODUTOS"
CREATE TABLE `loja_virtual`.`produtos` (
  `idprodutos` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL,
  `descricao` VARCHAR(100) NULL,
  `preco` DOUBLE NULL,
  `quantidade` INT NULL,
  PRIMARY KEY (`idprodutos`),
  UNIQUE INDEX `idprodutos_UNIQUE` (`idprodutos` ASC) VISIBLE);

-- INSERÇÃO DE DADOS DA TABELA "PRODUTOS"
INSERT INTO produtos (nome, descricao, preco, quantidade) VALUES
('Camiseta Básica', 'Camiseta de algodão unissex', 39.90, 50),
('Calça Jeans', 'Calça jeans azul tradicional', 119.90, 30),
('Tênis Esportivo', 'Tênis leve para corrida', 249.90, 20),
('Boné Preto', 'Boné ajustável em algodão', 59.90, 40),
('Jaqueta Corta-Vento', 'Jaqueta leve à prova d\'água', 199.90, 15),
('Mochila Escolar', 'Mochila resistente com 3 compartimentos', 149.90, 25),
('Relógio Digital', 'Relógio à prova d\'água com cronômetro', 89.90, 35),
('Fone de Ouvido Bluetooth', 'Fone sem fio com microfone', 129.90, 45),
('Carteira Couro', 'Carteira masculina em couro legítimo', 79.90, 28),
('Óculos de Sol', 'Óculos UV400 estilo retrô', 99.90, 22);

-- INSERÇÃO DE DADOS TESTE NA TABELA "PRODUTOS"
INSERT INTO produtos (nome, descricao, preco, quantidade) VALUES
('Camisa cavada', 'Camisa de algodão unissex em V', 39.90, 25);

-- RECUPERAÇÃO DOS DADOS DA TABELA "PRODUTOS"
select * from produtos;





