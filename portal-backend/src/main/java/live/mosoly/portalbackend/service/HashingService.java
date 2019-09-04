package live.mosoly.portalbackend.service;

import live.mosoly.portalbackend.model.RegInfoObject;
import live.mosoly.portalbackend.model.User;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Security;

@Service
public class HashingService {

    public String hash(User user) {
        return hash(user.getPhotoIdType() + user.getPhotoIdNumber());
    }

    public String hash(RegInfoObject user) {
        return hash(user.getPhotoIdType() + user.getPhotoIdNumber());

    }

    private String hash(String base) {
        Security.addProvider(new BouncyCastleProvider());
        Keccak.Digest256 digest256 = new Keccak.Digest256();
        byte[] hashbytes = digest256.digest(base.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(hashbytes));
    }

}
