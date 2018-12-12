/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashes;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import dto.HashDto;

/**
 *
 * @author STS
 */
public class HashGenerator {

    HashDto hashDto = new HashDto();

    public HashDto lettersHash(String ltrs) {
        HashFunction hfMur = Hashing.murmur3_128();
        HashCode hcMur = hfMur.newHasher()
                .putString(ltrs, Charsets.UTF_8)
                .hash();
        hashDto.setLetters256MurMur(hcMur.asInt() & 262143);

        HashFunction hfSha256 = Hashing.sha256();
        HashCode hcSha256 = hfSha256.newHasher()
                .putString(ltrs, Charsets.UTF_8)
                .hash();

        hashDto.setLetters256Sha(hcSha256.asInt() & 262143);

        HashFunction hfSha512 = Hashing.sha512();
        HashCode hcSha512 = hfSha512.newHasher()
                .putString(ltrs, Charsets.UTF_8)
                .hash();

        hashDto.setLetters512Sha(hcSha512.asInt() & 262143);

        return hashDto;
    }

    public static void main(String[] args) {
        HashDto dto = new HashGenerator().lettersHash("b");
        System.out.println(dto.getLetters256MurMur());
        System.out.println(dto.getLetters256Sha());
        System.out.println(dto.getLetters512Sha());
    }

    public HashDto binaryOpHash(String binaryOps) {
        HashFunction hfMur = Hashing.murmur3_128();
        HashCode hcMur = hfMur.newHasher()
                .putString(binaryOps, Charsets.UTF_8)
                .hash();
        hashDto.setBinaryOp256MurMur(hcMur.asInt() & 1023);

        HashFunction hfSha256 = Hashing.sha256();
        HashCode hcSha256 = hfSha256.newHasher()
                .putString(binaryOps, Charsets.UTF_8)
                .hash();

        hashDto.setBinaryOp256Sha(hcSha256.asInt() & 1023);

        HashFunction hfSha512 = Hashing.sha512();
        HashCode hcSha512 = hfSha512.newHasher()
                .putString(binaryOps, Charsets.UTF_8)
                .hash();

        hashDto.setBinaryOp512Sha(hcSha512.asInt() & 1023);

        return hashDto;

    }

    public HashDto numberHash(String numSrt) {
        HashFunction hfMur = Hashing.murmur3_128();
        HashCode hcMur = hfMur.newHasher()
                .putString(numSrt, Charsets.UTF_8)
                .hash();
        hashDto.setNum256MurMur(hcMur.asInt() & 2043);

        HashFunction hfSha256 = Hashing.sha256();
        HashCode hcSha256 = hfSha256.newHasher()
                .putString(numSrt, Charsets.UTF_8)
                .hash();

        hashDto.setNum256Sha(hcSha256.asInt() & 2043);

        HashFunction hfSha512 = Hashing.sha512();
        HashCode hcSha512 = hfSha512.newHasher()
                .putString(numSrt, Charsets.UTF_8)
                .hash();
         hashDto.setNum256Sha(hcSha512.asInt() & 2043);

        return hashDto;
    }

}
