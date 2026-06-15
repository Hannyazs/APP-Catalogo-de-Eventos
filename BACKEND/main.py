from fastapi import FastAPI
import sqlite3
from pydantic import BaseModel

app = FastAPI()

# ------------------------

conn = sqlite3.connect(
    "database.db",
    check_same_thread=False
)

cursor = conn.cursor()

cursor.execute(""" CREATE TABLE IF NOT EXISTS eventos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT,
    data TEXT,
    horario TEXT,
    local TEXT,
    descricao TEXT
    ) """)

conn.commit()

cursor.execute(""" CREATE TABLE IF NOT EXISTS inscricoes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nomeAluno TEXT,
    eventoId INTEGER
    ) """)

conn.commit()

# ------------------------

cursor.execute("SELECT COUNT(*) FROM eventos")

if cursor.fetchone()[0] == 0:

    eventos_iniciais = [

        (
            "Semana da Engenharia",
            "15/06/2026",
            "19:00",
            "Auditório Principal",
            "Palestras e workshops."
        ),

        (
            "Feira de Estágios",
            "20/06/2026",
            "14:00",
            "Bloco B",
            "Empresas recrutando alunos."
        ),

        (
            "Workshop de IA",
            "25/06/2026",
            "18:00",
            "Laboratório 4",
            "Introdução à IA."
        )

    ]

    cursor.executemany("""INSERT INTO eventos(nome,data,horario,local,descricao)VALUES (?, ?, ?, ?, ?)""", 
                       eventos_iniciais)
    conn.commit()

#-------------------

@app.get("/eventos")
def listar_eventos():

    cursor.execute("""SELECT * FROM eventos""")

    dados = cursor.fetchall()

    eventos = []

    for evento in dados:

        eventos.append({

            "id": evento[0],
            "nome": evento[1],
            "data": evento[2],
            "horario": evento[3],
            "local": evento[4],
            "descricao": evento[5]

        })

    return eventos

#-------------------

@app.get("/eventos/{id}")
def buscar_evento(id: int):

    cursor.execute(
        "SELECT * FROM eventos WHERE id=?",
        (id,)
    )

    evento = cursor.fetchone()

    if evento:

        return {

            "id": evento[0],
            "nome": evento[1],
            "data": evento[2],
            "horario": evento[3],
            "local": evento[4],
            "descricao": evento[5]

        }

    return {
        "erro": "Evento não encontrado"
    }

#-------------------

class Inscricao(BaseModel):

    nomeAluno: str

    eventoId: int

#-------------------

@app.post("/inscricoes")
def criar_inscricao(inscricao: Inscricao):

    cursor.execute("""INSERT INTO inscricoes (nomeAluno,eventoId) VALUES (?, ?)""",
                   (inscricao.nomeAluno,inscricao.eventoId))

    conn.commit()

    return {
        "mensagem": "Inscrição realizada"
    }

#-------------------

@app.get("/inscricoes")
def listar_inscricoes():

    cursor.execute(""" SELECT i.id, i.nomeAluno, e.nome, e.data, e.horario, e.local 
                   FROM inscricoes i INNER JOIN eventos e ON i.eventoId = e.id """)

    dados = cursor.fetchall()

    resultado = []

    for item in dados:

        resultado.append({
            "id": item[0],
            "nomeAluno": item[1],
            "nomeEvento": item[2],
            "data": item[3],
            "horario": item[4],
            "local": item[5]
        })

    return resultado

#------------------------

class EventoCreate(BaseModel):

    nome: str
    data: str
    horario: str
    local: str
    descricao: str

@app.post("/eventos")
def criar_evento(evento: EventoCreate):

    cursor.execute(""" INSERT INTO eventos (nome,data,horario,local,descricao) VALUES (?,?,?,?,?)""",
        ( evento.nome,
        evento.data,
        evento.horario,
        evento.local,
        evento.descricao )
    )

    conn.commit()

    return {
        "mensagem":"Evento criado"
    }

# ------------------------

@app.put("/eventos/{id}")
def atualizar_evento(id: int, evento: EventoCreate):
    cursor.execute(""" UPDATE eventos SET nome = ?, data = ?, horario = ?, local = ?, descricao = ? WHERE id = ? """,
                    (evento.nome, evento.data, evento.horario, evento.local, evento.descricao, id))
    
    conn.commit()
    
    if cursor.rowcount == 0:
        return {"erro": "Evento não encontrado"}
        
    return {"mensagem": "Evento atualizado com sucesso"}

# ------------------------

@app.delete("/eventos/{id}")
def deletar_evento(id: int):
    cursor.execute("DELETE FROM eventos WHERE id = ?", (id,))
    conn.commit()
    
    if cursor.rowcount == 0:
        return {"erro": "Evento não encontrado"}
        
    return {"mensagem": "Evento deletado com sucesso"}