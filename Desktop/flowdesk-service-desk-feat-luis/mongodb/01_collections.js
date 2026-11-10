// FlowDesk - Criação das Collections
// Atividade 3 - Modelagem de Dados no MongoDB

use("flowdesk");

// Remove as collections existentes para permitir
// a execução do script desde o início durante os testes.
db.usuarios.drop();
db.tickets.drop();
db.setores.drop();
db.categorias.drop();
db.status.drop();
db.papeis.drop();
db.comentarios.drop();
db.historicos.drop();
db.anexos.drop();
db.notificacoes.drop();

// Criação das collections principais do sistema.

db.createCollection("usuarios");
db.createCollection("tickets");
db.createCollection("setores");
db.createCollection("categorias");
db.createCollection("status");
db.createCollection("papeis");
db.createCollection("comentarios");
db.createCollection("historicos");
db.createCollection("anexos");
db.createCollection("notificacoes");

print("Collections do FlowDesk criadas com sucesso.");
