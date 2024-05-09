package it.polimi.ingsw.Server.Network;

import java.util.ArrayList;
import java.util.Arrays;

public record Message(MessageType messageType, String params) {}
