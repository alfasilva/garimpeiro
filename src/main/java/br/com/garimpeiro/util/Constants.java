/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.util;

import br.com.garimpeiro.entities.Categoria;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Cosntantes do sistema.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class Constants implements Serializable {

    public static final String PU = "garimpeiro_PU";    
    public static final String CAD_INF_COMP = "Informações complementares do Empreendedor.";
    public static final String CAD_SUCESSO = "Informações cadastradas com Sucesso!";
    public static final String CAD_ERRO = "Não foi possível cadastrar informações.";
    public static final String OBR_OPINIAO = "Obrigado pela sua opinião";
    public static final String MAIL_JA_CADASTRADO = "E-mail já cadastrado!";
    public static final String ERRO_BASE = "ERRO ao salvar informações";
    public static final String MAIL_IGUAL = "Campo e-mail deve ser igual!";
    public static final String IGUAIS = "Campos de confirmação precisa ser coerentes!";
    public static final String REGEX = "[^0-9]";
    public static final String URL_SITE = "garimpeiro-micheltiago.rhcloud.com";
    public static final String MAIL_USER = "garimpeiroboasvindas@gmail.com";
    public static final String MAIL_PASSWORD = "g4r1mp31r016";
    public static final String MAIL_HOST = "smtp.gmail.com";
    public static final String MAIL_PORT = "465";
    public static final String MAIL_SUBJECT_1 = "Garimpeiro Web - Boas Vindas !";
    public static final String MAIL_SUBJECT_2 = "Garimpeiro Web - Solicitação de troca de Senha !";
    public static final String TEXTO = "Deixe-nos explorar sua marca, expondo o seus serviços!";
    public static final String PRODUCT_NAME = "GarimpeiroWeb";
    public static final String URL_SERVIDOR = "http://www.garimpeiroweb.com.br/";
    public static final String URL_CONFIMACAO = URL_SERVIDOR + "confirmacao?token=";
    public static final String IMG_BOAS_VINDAS = "http://file1.garimpeiroweb.com.br/images/garimpeiro/garimpeiro-confirmacao-cad-02.jpg";
    public static final String CONFIRMACAO_ACERTO = "Obrigado por validar seu acesso.";
    public static final String CONFIRMACAO_ERRO = "Requisição inváida";
    public static final String CONFIRMACAO_ATUALIZACAO = "Dados atualizados com sucesso!";
    public static final String ERRO_ATUALIZACAO = "Não foi possível atualizados suas informações!";
    public static final String PRIMEIRA_IMG_URL = "images/avata/img_avatar2.png";
    public static final String ERRO_USER_SENHA = "Usuário ou Senha inválido";
    public static final String TMP_DIR = "/tmp/";
    public static final String FILE_SERVER_TARGET = "http://file1.garimpeiroweb.com.br/file/upload/";
    public static final String FILE_SERVER_IMAGE = "file1.garimpeiroweb.com.br/images/";
    public static final String FILE_SERVER_PROTOCOLO = "http://";
    public static final String IMG_SERVICO = "http://file1.garimpeiroweb.com.br/images/garimpeiro/garimpeiro-propaganda-01.png";
    public static final String TEXTO_SERVICO = "Parabéns por fazer parte do Garimpeiro, customize a descrição do seu Serviço.";
    public static final String SENHA = "Senha alterada com Sucesso!";
    public static final String SENHA_ERRO = "Senha antiga inválida!";
    public static final String SENHA_ENVIADA = "Uma nova senha foi enviada para o e-mail: ";
    public static final String EMAIL_INVALIDO = "Endereço eletrônico inválido: ";
    public static final String IMG_TROCA_SENHA = "http://file1.garimpeiroweb.com.br/images/garimpeiro/gen-icon-password.jpg";    
    /**
     * Retorna todos os UF
     *
     * @return
     */
    public static List<String> ufs() {
        List<String> ufs = new ArrayList<String>();
        ufs.add("AC");
        ufs.add("AL");
        ufs.add("AP");
        ufs.add("AM");
        ufs.add("BA");
        ufs.add("CE");
        ufs.add("DF");
        ufs.add("ES");
        ufs.add("GO");
        ufs.add("MA");
        ufs.add("MT");
        ufs.add("MS");
        ufs.add("MG");
        ufs.add("PA");
        ufs.add("PB");
        ufs.add("PR");
        ufs.add("PE");
        ufs.add("PI");
        ufs.add("RJ");
        ufs.add("RN");
        ufs.add("RS");
        ufs.add("RO");
        ufs.add("RR");
        ufs.add("SC");
        ufs.add("SP");
        ufs.add("SE");
        ufs.add("TO");
        return ufs;
    }

    /**
     * Retorna todas as Macro Categorias
     *
     * @return
     */
    public static List<Categoria> categorias() {
        List<Categoria> categorias = new ArrayList<Categoria>();
        categorias = new ArrayList<Categoria>();
        categorias.add(new Categoria(1, "MODA E BELEZA", "images/mode-beleza.jpg"));
        categorias.add(new Categoria(2, "SAÚDE E BEM ESTAR", "images/lavagem.jpg"));
        categorias.add(new Categoria(3, "FESTAS E EVENTOS", "images/corte-custura.jpg"));
        categorias.add(new Categoria(4, "SERVIÇOS AUTOMOTIVOS", "images/corte-custura.jpg"));
        categorias.add(new Categoria(5, "SERVIÇOS RESIDENCIAIS", "images/corte-custura.jpg"));
        categorias.add(new Categoria(6, "SERVIÇOS DIVERSOS", "images/corte-custura.jpg"));
        return categorias;
    }

}
