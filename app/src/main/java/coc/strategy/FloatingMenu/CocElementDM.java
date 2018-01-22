package coc.strategy.FloatingMenu;

/**
 * Created by XTO on 18/01/2018.
 */

public class CocElementDM
{
    int drawableResource = 0;
    boolean isNormal = false;
    boolean isDark = false;
    boolean isSpell = false;
    boolean isHeroe = false;
    public String kind = "";
    int space = 0;

    public int getDrawableResource()
    {
        return drawableResource;
    }

    public CocElementDM(int drawableResource)
    {
        this.drawableResource = drawableResource;
    }

    public void setNormal(boolean normal)
    {
        kind = "normal";
        isNormal = normal;
        unsetRest();
    }

    public void setDark(boolean dark)
    {
        isDark = dark;
        unsetRest();
        kind = "dark";
    }

    public void setHeroe(boolean heroe)
    {
        isHeroe = heroe;
        unsetRest();
        kind = "heroe";
    }

    public void setSpell(boolean spell)
    {
        isSpell = spell;
        unsetRest();
        kind = "spell";
    }
    private void unsetRest()
    {
        if(isNormal)
        {
            isDark = false;
            isHeroe = false;
            isSpell = false;
        }
        if(isDark)
        {
            isNormal = false;
            isHeroe = false;
            isSpell = false;
        }
        if(isHeroe)
        {
            isNormal = false;
            isDark = false;
            isSpell = false;
        }
        if(isSpell)
        {
            isNormal = false;
            isDark = false;
            isHeroe = false;
        }
    }
    public boolean getIsDark()
    {
        return isDark;
    }
    public boolean getIsSpell()
    {
        return isSpell;
    }
    public boolean getIsNormal()
    {
        return isNormal;
    }
    public boolean getIsHeroe()
    {
        return isHeroe;
    }
}
