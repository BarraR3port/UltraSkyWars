package io.github.Leonardo0013YT.UltraSkyWars.enums;

public enum ProjectileType {
    
    NOPROJ(false, false, false, false),
    YESPROJ(true, false, false, false),
    EXPROJ(true, true, false, false),
    DESPROJ(true, false, true, false),
    TELEPROJ(true, false, false, true);
    
    private final boolean appear;
    private final boolean explosive;
    private final boolean destructor;
    private final boolean teleporter;
    
    ProjectileType(boolean appear, boolean explosive, boolean destructor, boolean teleporter) {
        this.appear = appear;
        this.explosive = explosive;
        this.destructor = destructor;
        this.teleporter = teleporter;
    }
    
    public boolean isAppear() {
        return appear;
    }
    
    public boolean isExplosive() {
        return explosive;
    }
    
    public boolean isDestructor() {
        return destructor;
    }
    
    public boolean isTeleporter() {
        return teleporter;
    }
}