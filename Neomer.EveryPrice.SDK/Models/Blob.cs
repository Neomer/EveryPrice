using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public class Blob : BaseEntity, IBlob
    {

        public virtual DateTime CreationDate { get; set; }

        public virtual byte[] Data { get; set; }
        
    }
}
