/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constant;

/**
 *
 * @author STS
 */
public enum TokenType {
        // Token types cannot have underscores
        NUMBER("-?[0-9]+"), BINARYOP("[*|/|+|-|@|!|#|$|%|^|&|?]"), WHITESPACE("[ \t\f\r\n]+"),LETTER("[\\p{L}]+");

        public final String pattern;

        private TokenType(String pattern) {
            this.pattern = pattern;
        }
    }