using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public class Tag : BaseEntity, ITag
    {
        public virtual string Value { get; set; }
        
        public virtual IList<IShop> Shops { get; set; }
    }
}
