/*
 * Copyright 2016, 2017, 2018, 2019 FabricMC
 * Copyright 2022 QuiltMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.test.item;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Items;

import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;

public class ModifyItemAttributeModifiersCallbackTest implements ModInitializer {
	public static final int HEAD_SLOT_ID = 3;
	public static final EntityAttributeModifier MODIFIER = new EntityAttributeModifier("generic_max_health_modifier", 5.0, EntityAttributeModifier.Operation.ADDITION);

	@Override
	public void onInitialize(ModContainer mod) {
		ModifyItemAttributeModifiersCallback.EVENT.register((stack, slot, attributeModifiers) -> {
			if (stack.isOf(Items.DIAMOND_HELMET) && slot.getEntitySlotId() == HEAD_SLOT_ID) {
				attributeModifiers.put(EntityAttributes.GENERIC_MAX_HEALTH, MODIFIER);
			}
		});
	}
}
