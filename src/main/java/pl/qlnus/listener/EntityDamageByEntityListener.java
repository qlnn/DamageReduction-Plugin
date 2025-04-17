package pl.qlnus.listener;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Klasa: EntityDamageByEntityListener
 * Autor: qlnus
 * Data: 17.04.2025 15:29
 * Projekt: DamageReduction-Plugin
 */
public class EntityDamageByEntityListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            List<ItemStack> armor = Arrays.stream(player.getInventory().getArmorContents()).filter(Objects::nonNull).toList();
            double reduction = this.getReductionDamage(armor);
            event.setDamage(event.getDamage() * reduction);
        }
    }
    private double getReductionDamage(List<ItemStack> itemStacks) {
        double totalArmor = 0.0;
        for (ItemStack itemStack : itemStacks) {
            if (itemStack == null || !itemStack.hasItemMeta()) {
                continue;
            }
            ItemMeta meta = itemStack.getItemMeta();
            if (meta == null || !meta.hasAttributeModifiers()) {
                continue;
            }
            Collection<AttributeModifier> armorModifiers = meta.getAttributeModifiers(Attribute.GENERIC_ARMOR);
            if (armorModifiers != null && !armorModifiers.isEmpty()) {
                totalArmor += armorModifiers.stream()
                        .mapToDouble(AttributeModifier::getAmount)
                        .sum();
            }
        }
        double damageReduction = 1.0 - (totalArmor / 100.0);
        return (damageReduction <= 0.0) ? 0.01 : damageReduction;
    }
}
