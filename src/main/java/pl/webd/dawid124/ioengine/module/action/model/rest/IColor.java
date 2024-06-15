package pl.webd.dawid124.ioengine.module.action.model.rest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonDeserialize(as = Color.class)
@JsonSerialize(as = Color.class)
public interface IColor extends Serializable {

    int getR();

    void setR(int r);

    int getG();

    void setG(int g);

    int getB();

    void setB(int b);

    int getW();

    void setW(int w);

    int getWw();

    void setWw(int ww);

    void update(IColor color);
}
