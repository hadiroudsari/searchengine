/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz;

import connection.ConnectionGenerator;
import constant.Status;
import dto.GenericUrlDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hadi Vahabpour Roudsari
 */
public class UrlFacade {

    private final static  Logger MYLOG = Logger.getLogger(UrlFacade.class.getName());

    public void insertUrlAndBlooms(GenericUrlDto dto) {
        
        String sql = "INSERT INTO public.url(urlfullname, urlalltext, urlhashcode) VALUES ( ?, ?, ?);";
        try (Connection con = ConnectionGenerator.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            // String sql = "INSERT INTO public.url(urlfullname, urlalltext, urlhashcode) VALUES ( ?, ?, ?);";
//            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, dto.getUrlFullName());
            pstmt.setString(2, dto.getBodyPage());
            pstmt.setLong(3, dto.getHashText());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        //       new UrlFacade().insertBloom(dto, rs.getInt(1));
                       // MYLOG.log(Level.INFO, "   before inserting bloom  txnId : " + dto.getTxnId() + "    ----------------" + dto.getLetters() + "   " + "(dto.getNumbers()" + (dto.getNumbers()) + " dto.getBinaryOperand()" + dto.getBinaryOperand());
//                        String sqlBloom = "INSERT INTO public.bloomtype( letters, numbers, operands, bloomfid) VALUES ( " + showLetterAzAraay(dto.getLetters()) + ", " + showNumberAzAraay(dto.getNumbers()) + ", " + showOperandAzAraay(dto.getBinaryOperand()) + ", ?);";

                        String sqlBloom = "INSERT INTO public.bloomtype( " + (dto.getLetters() != null ? "letters," : "") + (dto.getNumbers() != null ? "numbers," : "") + (dto.getBinaryOperand() != null ? "operands," : "") + " bloomfid) "
                                + "VALUES ( " + (dto.getLetters() != null ? showLetterAsAraay(dto.getLetters()) + "," : "") + (dto.getNumbers() != null ? showNumberAsAraay(dto.getNumbers()) + "," : "") + (dto.getBinaryOperand() != null ? showOperandAsAraay(dto.getBinaryOperand()) + "," : "") + " ?);";

                        PreparedStatement pstmtBloom = con.prepareStatement(sqlBloom, Statement.RETURN_GENERATED_KEYS);

                        pstmtBloom.setInt(1, rs.getInt(1));
                        pstmtBloom.execute();
                        dto.setStatus(Status.INSERT);
                    }
                } catch (SQLException ex) {
                    dto.setStatus(Status.INSERTERROR);
                    MYLOG.log(Level.SEVERE, "  txnId : " + dto.getTxnId() + "     " + ex.toString());
                } catch (Exception e) {
                    dto.setStatus(Status.INSERTERROR);
                    MYLOG.log(Level.SEVERE, "  -txnId : " + dto.getTxnId() + "     " + e.toString());
                }
            }

        } catch (SQLException ex) {
            dto.setStatus(Status.INSERTERROR);
            MYLOG.log(Level.SEVERE, "  txnId : " + dto.getTxnId() + "     " + ex.toString());
        } catch (Exception e) {
            dto.setStatus(Status.INSERTERROR);
            MYLOG.log(Level.SEVERE, "  txnId : " + dto.getTxnId() + "     " + e.toString());
        } finally {
            MYLOG.log(Level.INFO, "   txnId : " + dto.getTxnId() + "   status code : " + dto.getStatus().getStatusCode());
        }

    }

    private String showLetterAsAraay(short[] letters) {
        StringBuilder sb = new StringBuilder(262144 * 2);
        sb.append("'{");
        for (int i = 0; i < letters.length; i++) {
            sb.append(letters[i]);
            if (i < letters.length - 1) {
                sb.append(",");
            } else {
                sb.append("}'");
            }
        }
        return sb.toString();

    }

    private String showNumberAsAraay(short[] letters) {
        StringBuilder sb = new StringBuilder(2048 * 2);
        sb.append("'{");
        for (int i = 0; i < letters.length; i++) {
            sb.append(letters[i]);
            if (i < letters.length - 1) {
                sb.append(",");
            } else {
                sb.append("}'");
            }
        }
        return sb.toString();

    }

    private String showOperandAsAraay(short[] letters) {
        StringBuilder sb = new StringBuilder(1024 * 2);
        sb.append("'{");
        for (int i = 0; i < letters.length; i++) {
            sb.append(letters[i]);
            if (i < letters.length - 1) {
                sb.append(",");
            } else {
                sb.append("}'");
            }
        }
        return sb.toString();

    }

}
