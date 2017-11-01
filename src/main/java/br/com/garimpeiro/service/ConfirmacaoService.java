/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.service;

import br.com.garimpeiro.dao.ConfirmacaoCadastroDao;
import br.com.garimpeiro.dao.ProfileDao;
import br.com.garimpeiro.entities.ConfirmacaoCadastro;
import br.com.garimpeiro.util.Constants;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * Servico que recebe a confirmacao por e-mail
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
@WebServlet(name = "Confirmacao", urlPatterns = "/confirmacao")
public class ConfirmacaoService extends HttpServlet implements Serializable {

    protected boolean processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean retorno = false;

        try {

            if (StringUtils.isNotBlank(request.getParameter("token"))) {
                String token = request.getParameter("token");
                EntityManagerFactory factory = Persistence.createEntityManagerFactory("garimpeiro_PU");
                ConfirmacaoCadastroDao daoConfCad = new ConfirmacaoCadastroDao(factory);
                ConfirmacaoCadastro confirmacao = daoConfCad.findByToken(token);
                if (confirmacao != null && confirmacao.getProfile() != null) {
                    ProfileDao profDao = new ProfileDao(factory);
                    confirmacao.getProfile().setAtivo(true);
                    profDao.update(confirmacao.getProfile());
                    retorno = true;
                }
                factory.close();
            }
        } catch (Exception e) {
            System.err.println("Token Invalido : " + e.getMessage());
        }
        return retorno;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        if (processRequest(req, resp)) {
            out.println("<html>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1'/>");
            out.println("<head>");
            out.println("<title>GarimpeiroWeb</title>");
            out.println("<link rel='stylesheet' href='http://www.w3schools.com/lib/w3.css'/>");
            out.println("<link rel='stylesheet' href='https://fonts.googleapis.com/icon?family=Material+Icons'/>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='w3-container'>");
            out.println("<i class='material-icons w3-text-orange' style='font-size:64px'>search</i> ");
            out.println("<p class='w3-animate-opacity'>" + Constants.CONFIRMACAO_ACERTO + "</p>");
            out.println("</div>");
            out.println("<div class='w3-container w3-black'>");
            out.println("<p>Acesse <a href='" + Constants.URL_SERVIDOR + "' target='_blank'>" + Constants.PRODUCT_NAME + "</a> o <b>maior</b> portal de prestadores de Serviço do <b>Brasil</b>.</p>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } else {
            out.println("<html>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1'/>");
            out.println("<head>");
            out.println("<title>GarimpeiroWeb</title>");
            out.println("<link rel='stylesheet' href='http://www.w3schools.com/lib/w3.css'/>");
            out.println("<link rel='stylesheet' href='https://fonts.googleapis.com/icon?family=Material+Icons'/>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='w3-container'>");
            out.println("<i class='material-icons w3-text-orange' style='font-size:64px'>search</i> ");
            out.println("<p class='w3-animate-opacity'>" + Constants.CONFIRMACAO_ERRO + "</p>");
            out.println("</div>");
            out.println("<div class='w3-container w3-black'>");
            out.println("<p>Acesse <a href='" + Constants.URL_SERVIDOR + "' target='_blank'>" + Constants.PRODUCT_NAME + "</a> o <b>maior</b> portal de prestadores de Serviço do <b>Brasil</b>.</p>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     *
     * @return
     */
    @Override
    public String getServletInfo() {
        return "Batman is you ?";
    }

}
