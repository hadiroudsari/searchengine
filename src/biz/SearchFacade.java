/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz;

import connection.ConnectionGenerator;
import core.CrawlerManager;
import dto.SearchDto;
import dto.TableDto;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextField;
import textprocessing.FullTextProcessing;


/**
 *
 * @author Hasdi Vahabpour Roudsari
 */
public class SearchFacade {

    SearchDto searchDto = new SearchDto();
    FullTextProcessing fullTextProcessing = new FullTextProcessing();
    List<TableDto> tableList = new ArrayList<>();
//    TableDto tableDto = new TableDto();
    private Logger myLog = Logger.getLogger(CrawlerManager.class.getName());

    public List<TableDto> search(String searchStr) {

        searchDto.setBodyPage(searchStr);
        fullTextProcessing.FullAnalyse(searchDto);
        storeKeysAndValues(searchDto.getLetters());
        String sql = "select * from search_bloom(?,?) inner join public.url on url.urlid=bloomfid order by mini desc limit 30";

        try (Connection con = ConnectionGenerator.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            Array arrayKey = con.createArrayOf("integer", searchDto.getKeys());
            Array arrayValue = con.createArrayOf("integer", searchDto.getValues());
            pstmt.setArray(1, arrayKey);
            pstmt.setArray(2, arrayValue);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TableDto tableDto = new TableDto();

                tableDto.setUrl(new JTextField(rs.getString("urlfullname")));
                tableDto.setWholeText(rs.getString("urlalltext"));
                tableDto.setProbableRank(Integer.parseInt(rs.getString("mini")));
                tableList.add(tableDto);

            }
         //   displayActor(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableList;
    }

//    private void displayActor(ResultSet rs) throws SQLException {
//        while (rs.next()) {
//            System.out.println(rs.getString("bloomid") + "\t"
//                    + rs.getString("bloomfid") + "\t"
//                    + rs.getString("mini") + "\t"
//                    + rs.getString("urlfullname"));
//
//        }
//    }

    private void storeKeysAndValues(short[] letters) {

        for (int i = 0; i < letters.length; i++) {
            if (letters[i] != 0) {

                searchDto.getKeyList().add(i + 1);
                searchDto.getValueList().add((int) letters[i]);

            }
        }
        searchDto.setKeys(new Integer[searchDto.getKeyList().size()]);
        searchDto.setValues(new Integer[searchDto.getKeyList().size()]);
        searchDto.getKeyList().toArray(searchDto.getKeys());
        searchDto.getValueList().toArray(searchDto.getValues());
        for (int j = 0; j < searchDto.getKeys().length; j++) {
            System.out.println(" - " + searchDto.getKeys()[j]);
            System.out.println(" - " + searchDto.getValues()[j]);
        }

    }

//    public static void main(String[] args) {
//        SearchFacade facade = new SearchFacade();
//        facade.search("Winston");
//    }
}
