package input;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class XBOXController {
    private Controller xboxController;
    private Component botonR1;
    private Component botonA;
    private Component joystick;
    private float joystickValue;
    private float deadzone = 0.2f;
    public XBOXController(){
        ControllerEnvironment env = ControllerEnvironment.getDefaultEnvironment();
        Controller[] controllers = env.getControllers();

        Controller xboxController = null;
        for (Controller controller : controllers){
            if(controller.getName().contains("Xbox")){
                xboxController = controller;
                break;
            }
        }

        /*if (xboxController != null){
            System.out.println("Xbox controller encontrado");
            for (net.java.games.input.Component component : xboxController.getComponents()){
                System.out.println("    "+component.getName()+ ": " +component.getIdentifier());
            }
        }else{
            System.out.println("Xbox controller not found");
        }*/

        if(xboxController != null){
            for(Component component : xboxController.getComponents()){
                switch (component.getName()){
                    case "Botón 5": //Boton R1
                        botonR1 = component;
                        break;
                    case "Botón 0": //Boton A
                        botonA = component;
                        break;
                    case "Eje X": //Stick derecho (largo)
                        joystick = component;
                        break;
                }
            }
        }
        this.xboxController = xboxController;
    }

    public boolean isBotonR1Pulsado(){
        if(botonR1 != null){
            xboxController.poll();
            return botonR1.getPollData() == 1.0f;
        }else{
            return false;
        }

    }
    public boolean isBotonAPulsado(){
        if(botonA != null){
            xboxController.poll();
            return botonA.getPollData() == 1.0f;
        }else{
            return false;
        }
    }
    public boolean isStickDerecha(){
        if(joystick != null){
            xboxController.poll();
            float currentValue = joystick.getPollData();
            if(currentValue > deadzone && currentValue >joystickValue){
                joystickValue = currentValue;
                return true;
            }
        }
        return false;
    }

    public boolean isStickIzquierda(){
        if(joystick != null){
            xboxController.poll();
            float currentValue = joystick.getPollData();
            if(currentValue < -deadzone && currentValue <joystickValue){
                joystickValue = currentValue;
                return true;
            }
        }
        return false;
    }

    public float getJoystickValue() {
        return joystickValue;
    }
}
