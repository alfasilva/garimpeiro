/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.util;

import br.com.garimpeiro.entities.Endereco;
import br.com.garimpeiro.vo.EnderecoViaCep;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Classe utilitaria responsavel por facilitar a chamada de servicos RESTs.
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public class RestClient implements Serializable {

    public static Endereco findCep(String cep) {

        try {
            URL url = new URL("http://viacep.com.br/ws/" + cep + "/json");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder jsonSb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonSb.append(line);
            }
            Gson gson = new Gson();
            EnderecoViaCep endereco = gson.fromJson(jsonSb.toString(), EnderecoViaCep.class);
            if (endereco != null && StringUtils.isNotBlank(endereco.getCep())) {
                return new Endereco(endereco.getCep().replaceAll("[^0-9]", ""), endereco.getLogradouro(), endereco.getComplemento(), endereco.getBairro(), endereco.getLocalidade(), endereco.getUf(), "", "");
            }
        } catch (Exception ex) {
            System.err.println("VIACEP nao localizou o CEP- " + ex);
        }
        return null;
    }

    public static String enviarArquivo(File file, String nome) {

        HttpClient client = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(Constants.FILE_SERVER_TARGET);
        try {
            MultipartEntity multiPartEntity = new MultipartEntity();
            multiPartEntity.addPart("fileName", new StringBody(nome != null ? nome : file.getName()));

            FileBody fileBody = new FileBody(file, "application/octect-stream");
            multiPartEntity.addPart("attachment", fileBody);
            postRequest.setEntity(multiPartEntity);
            HttpResponse response = client.execute(postRequest);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder jsonSb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonSb.append(line);
            }
            return Constants.FILE_SERVER_PROTOCOLO+Constants.FILE_SERVER_IMAGE+"users/"+jsonSb.toString();
        } catch (Exception ex) {
            System.out.println("ERRO :" + ex);
        }
        return null;
    }

}
