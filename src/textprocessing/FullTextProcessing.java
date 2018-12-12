/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textprocessing;

import constant.Constant;
import constant.Status;
import constant.TokenType;
import core.CrawlerManager;
import dto.GenericUrlDto;
import dto.HashDto;
import hashes.HashGenerator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author STS
 */
public class FullTextProcessing {

    private short[] letters = new short[262144];

    private short[] numbers = new short[2048];

    private short[] binaryOperand = new short[1024];

    private HashGenerator hashGenerator = new HashGenerator();

    private HashDto hashDto = new HashDto();

    private final static Logger MYLOG = Logger.getLogger(FullTextProcessing.class.getName());

    public void FullAnalyse(GenericUrlDto dto) {

        try {
            StringBuffer tokenPatternsBuffer = new StringBuffer();

            for (TokenType tokenType : TokenType.values()) {
                tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
            }
            Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));
            Matcher matcher = tokenPatterns.matcher(dto.getBodyPage());

            while (matcher.find()) {
                if (matcher.group(TokenType.LETTER.name()) != null) {
                    //     System.out.println("TokenType.LETTER.name()" + matcher.group(TokenType.LETTER.name()));
                    hashDto = hashGenerator.lettersHash(matcher.group(TokenType.LETTER.name()));
                    if (letters[hashDto.getLetters256MurMur()] == 32760) {
                        continue;
                    }
                    letters[hashDto.getLetters256MurMur()] += 1;//should prevent pver flow
                    letters[hashDto.getLetters256Sha()] += 1;
                    letters[hashDto.getLetters512Sha()] += 1;
                    dto.setLetters(letters);
                    continue;
                } else if (matcher.group(TokenType.BINARYOP.name()) != null) {
                    hashDto = hashGenerator.binaryOpHash(matcher.group(TokenType.BINARYOP.name()));
                    if (binaryOperand[hashDto.getBinaryOp256MurMur()] == 32767) {
                        continue;
                    }
                    binaryOperand[hashDto.getBinaryOp256MurMur()] += 1;
                    binaryOperand[hashDto.getBinaryOp256Sha()] += 1;
                    binaryOperand[hashDto.getBinaryOp512Sha()] += 1;
                    dto.setBinaryOperand(binaryOperand);
                    continue;
                } else if (matcher.group(TokenType.NUMBER.name()) != null) {
                    hashDto = hashGenerator.numberHash(matcher.group(TokenType.NUMBER.name()));
                    if (numbers[hashDto.getBinaryOp256MurMur()] == 32767) {
                        continue;
                    }
                    numbers[hashDto.getNum256MurMur()] += 1;
                    numbers[hashDto.getNum256Sha()] += 1;
                    numbers[hashDto.getNum512Sha()] += 1;
                    dto.setNumbers(numbers);

                    continue;
                } else if (matcher.group(TokenType.WHITESPACE.name()) != null) {
                    continue;
                }
            }
            dto.setStatus(Status.LEXICALANALYZE);
        } catch (Exception ex) {
            dto.setStatus(Status.LEXICALANALYZEERROR);
            MYLOG.log(Level.SEVERE, ex.toString());
        } finally {
            MYLOG.log(Level.INFO, " txnId : " + dto.getTxnId() + "     status code : " + dto.getStatus().getStatusCode());
        }
    }

}
