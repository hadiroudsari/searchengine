/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz;

import connection.ConnectionGenerator;
import constant.Constant;
import constant.Status;
import core.CrawlerManager;
import dto.GenericUrlDto;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author STS
 */
public class CheckNewUrl {

    private final static Logger MYLOG = Logger.getLogger(CheckNewUrl.class.getName());

    public CheckNewUrl() {
    }

    public void findUrlIfExists(GenericUrlDto dto) {

        String SQL = "SELECT  * FROM public.url where urlfullname = ?  ";
        try (Connection conn = ConnectionGenerator.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, dto.getUrlFullName());

            ResultSet rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                dto.setStatus(Status.NEED);
            }
            if (rs.isBeforeFirst()) {

                String SQL2 = "SELECT  * FROM public.url where urlfullname = ? and urlhashcode=? ";
                try (PreparedStatement pstmt2 = conn.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt2.setString(1, dto.getUrlFullName());
                    pstmt2.setLong(2, dto.getHashText());

                    ResultSet rs2 = pstmt2.executeQuery();

                    if (!rs2.isBeforeFirst()) {
                        dto.setStatus(Status.NEED);
                        String SQL3 = "DELETE FROM public.url WHERE urlid=? ; DELETE FROM public.bloomtype where bloomfid=?;";
                        try (PreparedStatement pstmt3 = conn.prepareStatement(SQL3)) {
                            if (rs.next()) {
                                pstmt3.setInt(1, rs.getInt("urlid"));
                                pstmt3.setInt(2, rs.getInt("urlid"));
                                int affectedrow = pstmt3.executeUpdate();
                                MYLOG.log(Level.INFO, affectedrow + " number of row deleted");
                            }

                        } catch (SQLException ex) {
                            dto.setStatus(Status.CHECKNEEDERROR);
                            MYLOG.log(Level.SEVERE, ex.toString());
                        } catch (Exception ex) {
                            MYLOG.log(Level.SEVERE, ex.toString());
                            dto.setStatus(Status.CHECKNEEDERROR);
                        }

                    }
                    if (rs2.isBeforeFirst()) {
                        dto.setStatus(Status.CHECKNOTNEED);
                    }

                } catch (SQLException ex) {
                    dto.setStatus(Status.CHECKNEEDERROR);
                    MYLOG.log(Level.SEVERE, ex.toString());
                } catch (Exception ex) {
                    MYLOG.log(Level.SEVERE, ex.toString());
                    dto.setStatus(Status.CHECKNEEDERROR);
                }

            }
        } catch (SQLException ex) {
            dto.setStatus(Status.CHECKNEEDERROR);
            MYLOG.log(Level.SEVERE, ex.toString());
        } catch (Exception ex) {
            dto.setStatus(Status.CHECKNEEDERROR);
            MYLOG.log(Level.SEVERE, ex.toString());
        } finally {
            MYLOG.log(Level.INFO, " txnId : " + dto.getTxnId() + "   status code : " + dto.getStatus().getStatusCode());
        }
    }

}
