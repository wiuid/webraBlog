package top.webra.admin.config;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.util.Configurable;
import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.WaterFilter;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 *
 * 给验证码塑形
 * @author webra
 */
public class NoWaterRippleConfig extends Configurable implements GimpyEngine {
    @Override
    public BufferedImage getDistortedImage(BufferedImage baseImage) {
        NoiseProducer noiseProducer = this.getConfig().getNoiseImpl();
        BufferedImage distortedImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), 2);
        Graphics2D graphics = (Graphics2D)distortedImage.getGraphics();
        RippleFilter rippleFilter = new RippleFilter();
        rippleFilter.setWaveType(2);
        rippleFilter.setXAmplitude(10.6F);
        rippleFilter.setYAmplitude(1.7F);
        rippleFilter.setXWavelength(15.0F);
        rippleFilter.setYWavelength(15.0F);
        rippleFilter.setEdgeAction(10);
        WaterFilter waterFilter = new WaterFilter();
        waterFilter.setAmplitude(10.5F);
        waterFilter.setPhase(10.0F);
        waterFilter.setWavelength(20.0F);
        BufferedImage effectImage = waterFilter.filter(baseImage, (BufferedImage)null);
        effectImage = rippleFilter.filter(effectImage, (BufferedImage)null);
        graphics.drawImage(baseImage, 0, 0, (Color)null, (ImageObserver)null);
        graphics.dispose();
        noiseProducer.makeNoise(distortedImage, 0.1F, 0.1F, 0.25F, 0.25F);
        noiseProducer.makeNoise(distortedImage, 0.1F, 0.25F, 0.5F, 0.9F);
        return distortedImage;
    }
}
