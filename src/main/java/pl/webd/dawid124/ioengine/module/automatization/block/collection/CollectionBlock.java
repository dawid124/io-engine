package pl.webd.dawid124.ioengine.module.automatization.block.collection;

import pl.webd.dawid124.ioengine.module.automatization.block.AbstractBlock;
import pl.webd.dawid124.ioengine.module.automatization.block.IBlock;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CollectionBlock extends AbstractBlock {

    private List<IBlock> blocs;

    @Override
    public void run(Map<String, Object> variables, String zoneId) {
        blocs.stream()
                .sorted(Comparator.comparing(IBlock::getOrder))
                .forEach(b -> b.run(variables, zoneId));
    }

    public List<IBlock> getBlocs() {
        return blocs;
    }

    public void setBlocs(List<IBlock> blocs) {
        this.blocs = blocs;
    }
}
