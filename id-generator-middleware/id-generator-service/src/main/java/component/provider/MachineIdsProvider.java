package component.provider;

public interface MachineIdsProvider extends MachineIdProvider {
    long getNextMachineId();
}
